package com.turi.infrastructure.common;

import com.turi.infrastructure.exception.BadRequestParameterException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public final class HashToken
{
    public static String hash(final String token)
    {
        try
        {
            final var digest = MessageDigest.getInstance("SHA-256");
            final var hash = digest.digest(token.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        }
        catch (final NoSuchAlgorithmException ex)
        {
            throw new BadRequestParameterException("Token hash error.");
        }
    }
}
