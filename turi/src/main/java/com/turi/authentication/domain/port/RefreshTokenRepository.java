package com.turi.authentication.domain.port;

import com.turi.authentication.domain.model.RefreshToken;

public interface RefreshTokenRepository
{
    RefreshToken findById(final Long id);

    RefreshToken findByToken(final String token);

    Long insert(final RefreshToken refreshToken);

    void delete(final Long id);

    void deleteByToken(final String token);

    void deleteAllExpiredRefreshTokens();
}
