package com.turi.user.domain.port;

import com.turi.user.domain.model.User;

public interface UserRepository
{
    User findById(final Long id);

    User findByUsername(final String username);

    User findByEmail(final String email);

    Long insert(final User user);

    void update(final Long userId, final User user);

    void delete(final Long userId);
}
