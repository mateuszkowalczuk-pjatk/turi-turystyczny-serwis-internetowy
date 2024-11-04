package com.turi.user.infrastructure.adapter.repository;

import com.turi.user.domain.exception.UserNotFoundException;
import com.turi.user.domain.model.User;
import com.turi.user.domain.port.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository
{
    private final UserRepositoryDao repositoryDao;

    @Override
    public List<User> findAll()
    {
        return repositoryDao.findAll().stream()
                .map(User::of)
                .toList();
    }

    @Override
    public User findById(final Long id)
    {
        return repositoryDao.findById(id)
                .map(User::of)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User findByUsername(final String username)
    {
        return repositoryDao.findByUsername(username)
                .map(User::of)
                .orElse(null);
    }

    @Override
    public User findByEmail(final String email)
    {
        return repositoryDao.findByEmail(email)
                .map(User::of)
                .orElse(null);
    }

    @Override
    public User findByPasswordResetToken(final String resetToken)
    {
        return repositoryDao.findByPasswordResetToken(resetToken)
                .map(User::of)
                .orElse(null);
    }

    @Override
    public Long insert(final User user)
    {
        final var entity = UserEntity.of(user);

        return repositoryDao.saveAndFlush(entity).getUserId();
    }

    @Override
    public void update(final Long id, final User user)
    {
        final var userEntity = repositoryDao.findById(id).orElse(null);

        final var entity = UserEntity.of(user);

        Optional.ofNullable(userEntity).ifPresent(e -> {
            e.setUsername(entity.getUsername());
            e.setEmail(entity.getEmail());
            e.setPassword(entity.getPassword());
            e.setPasswordResetCode(entity.getPasswordResetCode());
            e.setPasswordResetToken(entity.getPasswordResetToken());
            e.setPasswordResetExpiresAt(entity.getPasswordResetExpiresAt());

            repositoryDao.saveAndFlush(userEntity);
        });
    }
}
