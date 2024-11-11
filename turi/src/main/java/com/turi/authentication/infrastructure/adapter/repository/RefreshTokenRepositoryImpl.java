package com.turi.authentication.infrastructure.adapter.repository;

import com.turi.authentication.domain.exception.RefreshTokenNotFoundException;
import com.turi.authentication.domain.model.RefreshToken;
import com.turi.authentication.domain.port.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
@AllArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository
{
    private final RefreshTokenRepositoryDao repositoryDao;

    @Override
    public RefreshToken findById(final Long id)
    {
        return repositoryDao.findById(id)
                .map(RefreshToken::of)
                .orElseThrow(() -> new RefreshTokenNotFoundException(id));
    }

    @Override
    public RefreshToken findByToken(final String token)
    {
        return repositoryDao.findByToken(token)
                .map(RefreshToken::of)
                .orElse(null);
    }

    @Override
    public Long insert(final RefreshToken refreshToken)
    {
        final var entity = RefreshTokenEntity.of(refreshToken);

        return repositoryDao.saveAndFlush(entity).getRefreshTokenId();
    }

    @Override
    public void deleteById(final Long id)
    {
        final var refreshToken = findById(id);

        repositoryDao.deleteById(refreshToken.getRefreshTokenId());
    }

    @Transactional
    @Override
    public void deleteByToken(final String token)
    {
        repositoryDao.deleteByToken(token);
    }

    @Transactional
    @Override
    public void deleteAllExpiredRefreshTokens()
    {
        final var currentTime = LocalDateTime.now();

        repositoryDao.deleteAllByExpiresAtBefore(currentTime);
    }
}
