package com.turi.user.domain.port;

import com.turi.user.domain.model.User;

import java.util.List;

public interface UserRepository
{
    List<User> findAll();

    User findById(final Long id);

    User findByUsername(final String username);

    User findByEmail(final String email);

    User findByPasswordResetToken(final String resetToken);

    Long insert(final User user);

    void update(final Long id, final User user);

    void delete(final Long id);
}
