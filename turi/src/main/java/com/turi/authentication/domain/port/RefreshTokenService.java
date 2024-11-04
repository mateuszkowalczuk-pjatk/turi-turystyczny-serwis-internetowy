package com.turi.authentication.domain.port;

import com.turi.authentication.domain.model.RefreshToken;

public interface RefreshTokenService
{
    String generateRefreshToken(final Long userId);

    RefreshToken getById(final Long id);

    RefreshToken getByToken(final String token);

    Boolean isRefreshTokenExpired(final RefreshToken refreshToken);

    RefreshToken create(final RefreshToken refreshToken);

    void delete(final Long id);

    void deleteByToken(final String token);

    void deleteAllExpiredRefreshTokens();
}
