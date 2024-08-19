package com.turi.authentication.infrastructure.adapter.service;

import com.turi.account.domain.model.Account;
import com.turi.account.domain.model.AccountType;
import com.turi.account.infrastructure.adapter.interfaces.AccountFacade;
import com.turi.authentication.domain.exception.InvalidLoginException;
import com.turi.authentication.domain.exception.InvalidPasswordForLoginException;
import com.turi.authentication.domain.model.Authentication;
import com.turi.authentication.domain.port.AuthenticationJwtService;
import com.turi.authentication.domain.port.AuthenticationService;
import com.turi.authentication.infrastructure.adapter.application.queries.authentication.AuthenticationParam;
import com.turi.authentication.infrastructure.adapter.application.queries.registration.RegistrationParam;
import com.turi.infrastructure.config.SecurityProperties;
import com.turi.user.domain.model.User;
import com.turi.user.infrastructure.adapter.interfaces.UserFacade;
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
    private final AccountFacade accountFacade;
    private final SecurityProperties properties;
    private final AuthenticationManager authManager;
    private final AuthenticationJwtService jwtService;

    @Override
    public Account register(final RegistrationParam params)
    {
        final var user = User.builder()
                .withUsername(params.getUsername())
                .withEmail(params.getEmail())
                .withPassword(params.getPassword())
                .build();

        final var userId = userFacade.createUser(user).getUserId();

        final var account = Account.builder()
                .withUserId(userId)
                .withAccountType(AccountType.NORMAL)
                .build();

        return accountFacade.createAccount(account);
    }

    @Override
    public Authentication authenticate(final AuthenticationParam params)
    {
        try
        {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(params.getLogin(), params.getPassword()));

            final var token = jwtService.generateToken(params.getLogin());

            return Authentication.builder()
                    .withToken(token)
                    .withExpiresIn(properties.getExpirationTime())
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
}
