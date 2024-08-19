package com.turi.user.domain.port;

import com.turi.user.domain.model.User;

public interface UserService
{
    User getById(final Long id);

    User getByUsername(final String username);

    Boolean isUsernameExists(final String username);

    User getByEmail(final String email);

    Boolean isEmailExists(final String email);

    User createUser(final User user);

    User changeUsername(final Long userId, final String username);

    User changeEmail(final Long userId, final String email);

    User changePassword(final Long userId, final String password);
}
