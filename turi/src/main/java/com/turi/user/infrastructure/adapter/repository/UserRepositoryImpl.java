package com.turi.user.infrastructure.adapter.repository;

import com.turi.user.domain.exception.UserNotFoundException;
import com.turi.user.domain.model.User;
import com.turi.user.domain.port.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository
{
    private final UserRepositoryDao userRepositoryDao;

    @Override
    public User findById(final Long id)
    {
        return userRepositoryDao.findById(id)
                .map(User::of)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User findByUsername(final String username)
    {
        return userRepositoryDao.findByUsername(username)
                .map(User::of)
                .orElse(null);
    }

    @Override
    public User findByEmail(final String email)
    {
        return userRepositoryDao.findByEmail(email)
                .map(User::of)
                .orElse(null);
    }

    @Override
    public Long insert(final User user)
    {
        final var entity = UserEntity.of(user);

        return userRepositoryDao.saveAndFlush(entity).getUserId();
    }

    @Override
    public void update(final Long userId, final User user)
    {
        final var userEntity = userRepositoryDao.findById(userId).orElse(null);

        final var entity = UserEntity.of(user);

        Optional.ofNullable(userEntity).ifPresent(e -> {
            e.setUsername(entity.getUsername());
            e.setEmail(entity.getEmail());
            e.setPassword(entity.getPassword());

            userRepositoryDao.saveAndFlush(userEntity);
        });
    }

    @Override
    public void delete(final Long userId)
    {
        final var user = findById(userId);

        userRepositoryDao.deleteById(user.getUserId());
    }
}
