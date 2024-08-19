package com.turi.user.infrastructure.adapter.interfaces;

import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.user.domain.model.User;
import com.turi.user.domain.port.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserFacade
{
    private final UserService service;

    public Boolean isUsernameExists(final String username)
    {
        return UserResponse.of(service.isUsernameExists(username));
    }

    public Boolean isEmailExists(final String email)
    {
        return UserResponse.of(service.isEmailExists(email));
    }

    public User createUser(final User user)
    {
        passwordValidation(user.getPassword());

        return UserResponse.of(service.createUser(user));
    }

    public User changeUsername(final Long userId, final String username)
    {
        return UserResponse.of(service.changeUsername(userId, username));
    }

    public User changeEmail(final Long userId, final String email)
    {
        return UserResponse.of(service.changeEmail(userId, email));
    }

    public User changePassword(final Long userId, final String password)
    {
        passwordValidation(password);

        return UserResponse.of(service.changePassword(userId, password));
    }

    private void passwordValidation(final String password)
    {
        final var validator = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*?])[A-Za-z\\d!@#$%^&*?]{8,}$";

        if (password == null || !password.matches(validator))
        {
            throw new BadRequestParameterException("Invalid password. Password must be at least 8 characters long, containing at least one lowercase letter, one uppercase letter, one digit, and one special character (!@#$%^&*?).");
        }
    }
}
