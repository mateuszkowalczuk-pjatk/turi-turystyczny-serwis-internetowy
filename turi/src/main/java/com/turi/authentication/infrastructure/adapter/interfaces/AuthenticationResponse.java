package com.turi.authentication.infrastructure.adapter.interfaces;

import com.turi.authentication.domain.model.Authentication;
import io.jsonwebtoken.lang.Assert;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthenticationResponse
{
    public static Authentication of(final Authentication authentication)
    {
        Assert.notNull(authentication, "Authentication response must not be null.");

        return authentication;
    }
}
