package com.turi.authentication.infrastructure.adapter.service;

import com.turi.account.domain.model.Account;
import com.turi.account.domain.model.AccountType;
import com.turi.account.infrastructure.adapter.interfaces.AccountFacade;
import com.turi.authentication.domain.exception.InvalidLoginException;
import com.turi.authentication.domain.exception.InvalidPasswordForLoginException;
import com.turi.authentication.domain.exception.RefreshTokenExpiredException;
import com.turi.authentication.domain.exception.RefreshTokenNotFoundByTokenException;
import com.turi.authentication.domain.model.Authentication;
import com.turi.authentication.domain.port.AuthenticationService;
import com.turi.authentication.domain.port.JwtService;
import com.turi.authentication.domain.port.RefreshTokenService;
import com.turi.authentication.infrastructure.adapter.application.queries.authentication.AuthenticationParam;
import com.turi.authentication.infrastructure.adapter.application.queries.logout.LogoutParam;
import com.turi.authentication.infrastructure.adapter.application.queries.refresh.RefreshParam;
import com.turi.authentication.infrastructure.adapter.application.queries.registration.RegistrationParam;
import com.turi.infrastructure.config.SecurityProperties;
import com.turi.user.domain.model.User;
import com.turi.user.infrastructure.adapter.interfaces.UserFacade;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService
{
    private final UserFacade userFacade;
    private final JwtService jwtService;
    private final AccountFacade accountFacade;
    private final SecurityProperties properties;
    private final AuthenticationManager authManager;
    private final RefreshTokenService refreshTokenService;

    @Override
    public Authentication register(final RegistrationParam params)
    {
        final var user = User.builder()
                .withUsername(params.getUsername())
                .withEmail(params.getEmail())
                .withPassword(params.getPassword())
                .build();

        final var userId = userFacade.createUser(user).getUserId();

        final var account = Account.builder()
                .withUserId(userId)
                .withAccountType(AccountType.INACTIVE)
                .build();

        final var accountId = accountFacade.createAccount(account).getAccountId();

        return getActivateToken(accountId, account.getAccountType().getName());
    }

    @Override
    public Authentication authenticate(final AuthenticationParam params)
    {
        try
        {
            final var authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(params.getLogin(), params.getPassword())
            );

            final var userId = userFacade.getUserIdByLogin(authentication.getName());

            final var account = accountFacade.getAccountByUserId(userId);

            final var role = account.getAccountType().getName();

            if (role.equals(AccountType.INACTIVE.getName()))
            {
                return getActivateToken(account.getAccountId(), role);
            }

            final var accessToken = jwtService.generateToken(account.getAccountId(), role);

            final var refreshToken = refreshTokenService.generateRefreshToken(userId);

            return Authentication.builder()
                    .withAccessToken(accessToken)
                    .withRefreshToken(refreshToken)
                    .withAccessTokenExpiresIn(properties.getAccessTokenExpirationTime())
                    .withRefreshTokenExpiresIn(properties.getRefreshTokenExpirationTime())
                    .build();
        }
        catch (final InternalAuthenticationServiceException ex)
        {
            throw new InvalidLoginException(params.getLogin());
        }
        catch (final BadCredentialsException ex)
        {
            throw new InvalidPasswordForLoginException(params.getLogin());
        }
    }

    private Authentication getActivateToken(final Long subject, final String role)
    {
        final var activateToken = jwtService.generateToken(subject, role);

        return Authentication.builder()
                .withAccessToken(activateToken)
                .withRefreshTokenExpiresIn(properties.getAccessTokenExpirationTime())
                .build();
    }

    @Override
    public Authentication refresh(final RefreshParam params)
    {
        final var refreshToken = refreshTokenService.getByToken(params.getRefreshToken());

        if (refreshToken == null)
        {
            throw new RefreshTokenNotFoundByTokenException(params.getRefreshToken());
        }

        if (refreshTokenService.isRefreshTokenExpired(refreshToken))
        {
            refreshTokenService.deleteById(refreshToken.getRefreshTokenId());

            throw new RefreshTokenExpiredException();
        }

        final var account = accountFacade.getAccountByUserId(refreshToken.getUserId());

        final var accessToken = jwtService.generateToken(account.getAccountId(), account.getAccountType().getName());

        return Authentication.builder()
                .withAccessToken(accessToken)
                .withAccessTokenExpiresIn(properties.getAccessTokenExpirationTime())
                .build();
    }

    @Override
    public HttpServletResponse logout(final LogoutParam params)
    {
        refreshTokenService.deleteByToken(params.getRefreshToken());

        return params.getResponse();
    }
}
