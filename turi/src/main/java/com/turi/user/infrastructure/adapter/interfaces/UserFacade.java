package com.turi.user.infrastructure.adapter.interfaces;

import com.turi.infrastructure.common.ContextHandler;
import com.turi.infrastructure.common.ObjectId;
import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.user.domain.model.User;
import com.turi.user.domain.port.UserService;
import jakarta.servlet.http.HttpServletResponse;
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

        return service.getUserIdByLogin(login);
    }

    public ResponseEntity<String> getUserUsername()
    {
        final var userId = ContextHandler.getIdFromContext();

        return UserResponse.of(service.getUsername(userId));
    }

    public ResponseEntity<String> getUserEmail()
    {
        final var userId = ContextHandler.getIdFromContext();

        return UserResponse.of(service.getEmail(userId));
    }

    public ResponseEntity<String> getUserEmailById(final String id)
    {
        if (id == null)
        {
            throw new BadRequestParameterException("Parameter ID must not be null.");
        }

        final var userId = ObjectId.of(id).getValue();

        return UserResponse.of(service.getEmail(userId));
    }

    public String getUserEmailByUserId(final Long userId)
    {
        if (userId == null)
        {
            throw new BadRequestParameterException("Parameter userId must not be null.");
        }

        return service.getEmail(userId);
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

    public ResponseEntity<?> resetUserPassword(final String resetToken, final String code, final HttpServletResponse response)
    {
        if (resetToken == null || code == null)
        {
            throw new BadRequestParameterException("Parameters resetToken and code must not be null.");
        }

        return UserResponse.of(service.resetPassword(resetToken, Integer.valueOf(code)), response);
    }

    public User createUser(final User user)
    {
        if (user == null)
        {
            throw new BadRequestParameterException("Parameter user must not be null.");
        }

        emailValidation(user.getEmail());

        passwordValidation(user.getPassword());

        return service.create(user);
    }

    public ResponseEntity<?> changeUserUsername(final String username)
    {
        if (username == null)
        {
            throw new BadRequestParameterException("Parameter username must not be null.");
        }

        final var userId = ContextHandler.getIdFromContext();

        service.changeUsername(userId, username);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> changeUserEmail(final String email)
    {
        if (email == null)
        {
            throw new BadRequestParameterException("Parameter email must not be null.");
        }

        final var userId = ContextHandler.getIdFromContext();

        emailValidation(email);

        service.changeEmail(userId, email);

        return ResponseEntity.ok().build();
    }

    private void emailValidation(final String email)
    {
        final var validator = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

        if (email == null || !email.matches(validator))
        {
            throw new BadRequestParameterException("Invalid email. Email must be in form example@example.com.");
        }
    }

    public ResponseEntity<?> changeUserPassword(final String password)
    {
        if (password == null)
        {
            throw new BadRequestParameterException("Parameter password must not be null.");
        }

        final var userId = ContextHandler.getIdFromContext();

        passwordValidation(password);

        service.changePassword(userId, password);

        return ResponseEntity.ok().build();
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
