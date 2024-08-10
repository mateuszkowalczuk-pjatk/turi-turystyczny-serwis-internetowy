package com.turi.authentication.domain.port;

public interface AuthenticationJwtService
{
    String generateToken(final String subject);
}
