package com.turi.authentication.infrastructure.adapter.interfaces;

import com.turi.authentication.domain.model.LoginParam;
import com.turi.authentication.domain.model.LogoutParam;
import com.turi.authentication.domain.model.RefreshParam;
import com.turi.authentication.domain.model.RegisterParam;
import com.turi.authentication.domain.port.AuthenticationService;
import com.turi.infrastructure.exception.BadRequestParameterException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthenticationFacade
{
    private final AuthenticationService service;

    public ResponseEntity<?> register(final RegisterParam params)
    {
        if (params == null || params.getUsername() == null || params.getEmail() == null || params.getPassword() == null)
        {
            throw new BadRequestParameterException("Parameters username, email and password are required.");
        }

        return AuthenticationResponse.of(service.register(params));
    }

    public ResponseEntity<?> login(final LoginParam params)
    {
        if (params == null || params.getLogin() == null || params.getPassword() == null)
        {
            throw new BadRequestParameterException("Parameters login and password are required.");
        }

        return AuthenticationResponse.of(service.login(params));
    }

    public ResponseEntity<?> authorize()
    {
        return AuthenticationResponse.of(service.authorize());
    }

    public ResponseEntity<?> refresh(final RefreshParam params)
    {
        if (params == null || params.getRefreshToken() == null)
        {
            throw new BadRequestParameterException("Parameter refreshToken is required.");
        }

        return AuthenticationResponse.of(service.refresh(params));
    }

    public ResponseEntity<?> logout(final LogoutParam params)
    {
        if (params == null || params.getRefreshToken() == null)
        {
            throw new BadRequestParameterException("Parameter refreshToken is required.");
        }

        return AuthenticationResponse.of(service.logout(params));
    }
}
