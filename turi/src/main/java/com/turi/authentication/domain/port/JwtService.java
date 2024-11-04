package com.turi.authentication.domain.port;

public interface JwtService
{
    String generateToken(final Long subject, final String role);
}
