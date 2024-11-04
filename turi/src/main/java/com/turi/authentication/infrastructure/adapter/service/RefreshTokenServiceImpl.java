package com.turi.authentication.infrastructure.adapter.service;

import com.turi.authentication.domain.exception.RefreshTokenExpiredException;
import com.turi.authentication.domain.model.RefreshToken;
import com.turi.authentication.domain.port.RefreshTokenRepository;
import com.turi.authentication.domain.port.RefreshTokenService;
import com.turi.infrastructure.common.HashToken;
import com.turi.infrastructure.config.SecurityProperties;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService
{
    private final SecurityProperties properties;
    private final RefreshTokenRepository repository;

    @Override
    public String generateRefreshToken(final Long userId)
    {
        final var token = properties.getSecretKey() + "-" + userId + "-" + UUID.randomUUID();

        final var refreshToken = RefreshToken.builder()
                .withUserId(userId)
                .withToken(HashToken.hash(token))
                .withExpiresAt(LocalDateTime.now().plusSeconds(properties.getRefreshTokenExpirationTime()))
                .build();

        return create(refreshToken).getToken();
    }

    @Override
    public RefreshToken getById(final Long id)
    {
        return repository.findById(id);
    }

    @Override
    public RefreshToken getByToken(final String token)
    {
        return repository.findByToken(token);
    }

    @Override
    public Boolean isRefreshTokenExpired(final RefreshToken refreshToken)
    {
        return refreshToken.getExpiresAt().isBefore(LocalDateTime.now());
    }

    @Override
    public RefreshToken create(final RefreshToken refreshToken)
    {
        final var refreshTokenId = repository.insert(refreshToken);

        return getById(refreshTokenId);
    }

    @Override
    public void delete(final Long id)
    {
        if (getById(id) == null)
        {
            throw new RefreshTokenExpiredException();
        }

        repository.delete(id);
    }

    @Override
    public void deleteByToken(final String token)
    {
        if (getByToken(HashToken.hash(token)) == null)
        {
            throw new RefreshTokenExpiredException();
        }

        repository.deleteByToken(token);
    }

    @Override
    public void deleteAllExpiredRefreshTokens()
    {
        repository.deleteAllExpiredRefreshTokens();
    }
}
