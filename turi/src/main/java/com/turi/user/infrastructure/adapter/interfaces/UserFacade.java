package com.turi.user.infrastructure.adapter.interfaces;

import com.turi.infrastructure.common.ContextHandler;
import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.infrastructure.exception.BadRequestResponseException;
import com.turi.user.domain.model.User;
import com.turi.user.domain.port.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserFacade
{
    private final UserService service;

    public User getUserById(final Long id)
    {
        if (id == null)
        {
            throw new BadRequestParameterException("Parameter ID must not be null.");
        }

        return service.getById(id);
    }

    public Long getUserIdByLogin(final String login)
    {
        if (login == null)
        {
            throw new BadRequestParameterException("Parameter login must not be null.");
        }

        final var user = service.getUserIdByLogin(login);

        if (user == null)
        {
            throw new BadRequestResponseException("User response must not be null.");
        }

        return user;
    }

    public ResponseEntity<Boolean> isUserUsernameExists(final String username)
    {
        if (username == null)
        {
            throw new BadRequestParameterException("Parameter username must not be null.");
        }

        return UserResponse.of(service.isUsernameExists(username));
    }

    public ResponseEntity<Boolean> isUserEmailExists(final String email)
    {
        if (email == null)
        {
            throw new BadRequestParameterException("Parameter email must not be null.");
        }

        return UserResponse.of(service.isEmailExists(email));
    }

    public ResponseEntity<?> sendResetUserPasswordCode(final String email)
    {
        if (email == null)
        {
            throw new BadRequestParameterException("Parameter email must not be null.");
        }

        return UserResponse.of(service.sendResetPasswordCode(email));
    }

    public ResponseEntity<?> resetUserPassword(final String resetToken, final String code)
    {
        if (code == null)
        {
            throw new BadRequestParameterException("Parameters resetToken and code must not be null.");
        }

        return UserResponse.of(service.resetPassword(resetToken, Integer.valueOf(code)));
    }

    public User createUser(final User user)
    {
        if (user == null)
        {
            throw new BadRequestParameterException("Parameter user must not be null.");
        }

        emailValidation(user.getEmail());

        passwordValidation(user.getPassword());

        final var result = service.create(user);

        if (result == null)
        {
            throw new BadRequestResponseException("User response must not be null.");
        }

        return result;
    }

    public ResponseEntity<User> changeUserUsername(final String username)
    {
        if (username == null)
        {
            throw new BadRequestParameterException("Parameter username must not be null.");
        }

        final var userId = ContextHandler.getIdFromContext();

        return UserResponse.of(service.changeUsername(userId, username));
    }

    public ResponseEntity<User> changeUserEmail(final String email)
    {
        if (email == null)
        {
            throw new BadRequestParameterException("Parameter email must not be null.");
        }

        final var userId = ContextHandler.getIdFromContext();

        emailValidation(email);

        return UserResponse.of(service.changeEmail(userId, email));
    }

    public ResponseEntity<User> changeUserPassword(final String password)
    {
        if (password == null)
        {
            throw new BadRequestParameterException("Parameter password must not be null.");
        }

        final var userId = ContextHandler.getIdFromContext();

        passwordValidation(password);

        return UserResponse.of(service.changePassword(userId, password));
    }

    private void emailValidation(final String email)
    {
        final var validator = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

        if (email == null || !email.matches(validator))
        {
            throw new BadRequestParameterException("Invalid email. Email must be in form example@example.com.");
        }
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
