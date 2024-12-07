package com.turi.authentication.infrastructure.adapter.service;

import com.turi.account.domain.exception.AccountActivationCodeRecentlySentException;
import com.turi.account.domain.exception.AccountNotFoundException;
import com.turi.account.domain.model.Account;
import com.turi.account.domain.model.AccountType;
import com.turi.account.infrastructure.adapter.interfaces.AccountFacade;
import com.turi.authentication.domain.exception.InvalidLoginException;
import com.turi.authentication.domain.exception.InvalidPasswordForLoginException;
import com.turi.authentication.domain.exception.RefreshTokenExpiredException;
import com.turi.authentication.domain.exception.RefreshTokenNotFoundByTokenException;
import com.turi.authentication.domain.model.*;
import com.turi.authentication.domain.port.AuthenticationService;
import com.turi.authentication.domain.port.JwtService;
import com.turi.authentication.domain.port.RefreshTokenService;
import com.turi.infrastructure.config.SecurityProperties;
import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.premium.infrastructure.adapter.interfaces.PremiumFacade;
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
    private final PremiumFacade premiumFacade;
    private final SecurityProperties properties;
    private final AuthenticationManager authManager;
    private final RefreshTokenService refreshTokenService;

    @Override
    public Authentication register(final RegisterParam params)
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
    public Authentication login(final LoginParam params)
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
                try
                {
                    accountFacade.sendAccountActivateCode(account.getAccountId());
                }
                catch (AccountActivationCodeRecentlySentException ex)
                {
                    return getActivateToken(account.getAccountId(), role);
                }

                return getActivateToken(account.getAccountId(), role);
            }

            if (role.equals(AccountType.PREMIUM.getName()))
            {
                final var premiumLogin = premiumFacade.sendPremiumLoginCode(account.getAccountId(), userFacade.getUserEmailByUserId(userId));

                return Authentication.builder()
                        .withLoginToken(premiumLogin.getLoginToken())
                        .withAccessTokenExpiresIn(premiumLogin.getLoginTokenExpiresIn())
                        .build();
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
    public Authentication loginPremium(final String loginToken, final Integer code)
    {
        final var accountId = premiumFacade.loginIntoPremiumAccount(loginToken, code);

        final var accessToken = jwtService.generateToken(accountId, AccountType.PREMIUM.getName());

        final var refreshToken = refreshTokenService.generateRefreshToken(accountId);

        return Authentication.builder()
                .withAccessToken(accessToken)
                .withRefreshToken(refreshToken)
                .withAccessTokenExpiresIn(properties.getAccessTokenExpirationTime())
                .withRefreshTokenExpiresIn(properties.getRefreshTokenExpirationTime())
                .build();
    }

    @Override
    public Boolean authorize()
    {
        try
        {
            accountFacade.getAccountById();
        }
        catch (final AccountNotFoundException | BadRequestParameterException ex)
        {
            return false;
        }

        return true;
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
