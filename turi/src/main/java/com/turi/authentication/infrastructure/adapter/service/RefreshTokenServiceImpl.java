package com.turi.authentication.infrastructure.adapter.service;

import com.turi.authentication.domain.exception.RefreshTokenExpiredException;
import com.turi.authentication.domain.model.RefreshToken;
import com.turi.authentication.domain.port.RefreshTokenRepository;
import com.turi.authentication.domain.port.RefreshTokenService;
import com.turi.authentication.infrastructure.config.SecurityProperties;
import com.turi.infrastructure.common.HashToken;
import com.turi.infrastructure.exception.BadRequestParameterException;
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
        if (userId == null)
        {
            throw new BadRequestParameterException("UserId must not be null.");
        }

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
        if (id == null)
        {
            throw new BadRequestParameterException("Id must not be null.");
        }

        return repository.findById(id);
    }

    @Override
    public RefreshToken getByToken(final String token)
    {
        if (token == null)
        {
            throw new BadRequestParameterException("Token must not be null.");
        }

        return repository.findByToken(token);
    }

    @Override
    public Boolean isRefreshTokenExpired(final RefreshToken refreshToken)
    {
        if (refreshToken == null)
        {
            throw new BadRequestParameterException("RefreshToken must not be null.");
        }

        return refreshToken.getExpiresAt().isBefore(LocalDateTime.now());
    }

    @Override
    public RefreshToken create(final RefreshToken refreshToken)
    {
        if (refreshToken == null)
        {
            throw new BadRequestParameterException("UserId must not be null.");
        }

        final var refreshTokenId = repository.insert(refreshToken);

        return getById(refreshTokenId);
    }

    @Override
    public void deleteById(final Long id)
    {
        if (id == null)
        {
            throw new BadRequestParameterException("RefreshTokenId must not be null.");
        }

        final var refreshTokenId = getById(id).getRefreshTokenId();

        repository.deleteById(refreshTokenId);
    }

    @Override
    public void deleteByToken(final String token)
    {
        if (token == null)
        {
            throw new BadRequestParameterException("Token must not be null.");
        }

        if (getByToken(token) == null || isRefreshTokenExpired(getByToken(token)))
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
