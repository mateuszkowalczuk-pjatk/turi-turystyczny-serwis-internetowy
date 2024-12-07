package com.turi.user.infrastructure.adapter.service;

import com.turi.authentication.domain.port.RefreshTokenService;
import com.turi.infrastructure.common.CodeGenerator;
import com.turi.infrastructure.common.EmailSender;
import com.turi.infrastructure.common.HashToken;
import com.turi.infrastructure.properties.SecurityProperties;
import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.user.domain.exception.*;
import com.turi.user.domain.model.RefreshToken;
import com.turi.user.domain.model.ResetToken;
import com.turi.user.domain.model.User;
import com.turi.user.domain.port.UserRepository;
import com.turi.user.domain.port.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService
{
    private final EmailSender emailSender;
    private final UserRepository repository;
    private final SecurityProperties properties;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;

    @Override
    public User getById(final Long id)
    {
        return repository.findById(id);
    }

    @Override
    public User getByUsername(final String username)
    {
        return repository.findByUsername(username);
    }

    @Override
    public User getByEmail(final String email)
    {
        return repository.findByEmail(email);
    }

    @Override
    public String getUsername(final Long id)
    {
        final var user = getById(id);

        return user.getUsername();
    }

    @Override
    public String getEmail(final Long id)
    {
        final var user = getById(id);

        return user.getEmail();
    }

    @Override
    public User getByPasswordResetToken(final String passwordResetToken)
    {
        return repository.findByPasswordResetToken(passwordResetToken);
    }

    @Override
    public Long getUserIdByLogin(final String login)
    {
        final var user = Optional.ofNullable(getByUsername(login))
                .orElseGet(() -> Optional.ofNullable(getByEmail(login))
                .orElseThrow(() -> new UserNotFoundByLoginException(login)));

        return user.getUserId();
    }

    @Override
    public Boolean isUsernameExists(final String username)
    {
        return getByUsername(username) != null;
    }

    @Override
    public Boolean isEmailExists(final String email)
    {
        return getByEmail(email) != null;
    }

    @Override
    public ResetToken sendResetPasswordCode(final String email)
    {
        final var user = getByEmail(email);

        if (user == null)
        {
            throw new UserNotFoundByEmailException(email);
        }

        if (user.getPasswordResetExpiresAt() == null || user.getPasswordResetExpiresAt().minusMinutes(10).isBefore(LocalDateTime.now()))
        {
            final var code = CodeGenerator.generateCode();

            final var token = properties.getSecretKey() + "-" + user.getUserId() + "-" + UUID.randomUUID();

            user.setPasswordResetCode(code);
            user.setPasswordResetToken(HashToken.hash(token));
            user.setPasswordResetExpiresAt(LocalDateTime.now().plusSeconds(properties.getAccessTokenExpirationTime()));

            repository.update(user.getUserId(), user);

            emailSender.sendEmail(email, "User password reset code.", user.getPasswordResetCode());

            return ResetToken.builder()
                    .withToken(user.getPasswordResetToken())
                    .withExpiresIn(properties.getAccessTokenExpirationTime())
                    .build();
        }
        else
        {
            throw new UserResetCodeRecentlySentException();
        }
    }

    @Override
    public RefreshToken resetPassword(final String resetToken, final Integer code)
    {
        final var user = getByPasswordResetToken(resetToken);

        if (user == null)
        {
            throw new UserNotFoundByResetTokenException(resetToken);
        }

        if (!user.getPasswordResetCode().equals(code))
        {
            throw new BadRequestParameterException("Invalid user reset code.");
        }

        if (user.getPasswordResetExpiresAt().isAfter(LocalDateTime.now()))
        {
            deletePasswordResetDetails(user);

            final var token = refreshTokenService.generateRefreshToken(user.getUserId());

            return RefreshToken.builder()
                    .withRefreshToken(token)
                    .withRefreshTokenExpiresIn(properties.getRefreshTokenExpirationTime())
                    .build();
        }
        else
        {
            deletePasswordResetDetails(user);

            throw new UserResetCodeExpiredException();
        }
    }

    @Override
    public User create(final User user)
    {
        if (isUsernameExists(user.getUsername()))
        {
            throw new UserUniqueUsernameException(user.getUsername());
        }

        if (isEmailExists(user.getEmail()))
        {
            throw new UserUniqueEmailException(user.getEmail());
        }

        if (user.getPassword() == null)
        {
            throw new BadRequestParameterException("Parameter password is required.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        final var userId = repository.insert(user);

        return getById(userId);
    }

    @Override
    public User changeUsername(final Long id, final String username)
    {
        if (isUsernameExists(username))
        {
            throw new UserUniqueUsernameException(username);
        }

        final var user = getById(id);

        user.setUsername(username);

        repository.update(id, user);

        return getById(id);
    }

    @Override
    public User changeEmail(final Long id, final String email)
    {
        if (isEmailExists(email))
        {
            throw new UserUniqueEmailException(email);
        }

        final var user = getById(id);

        user.setEmail(email);

        repository.update(id, user);

        return getById(id);
    }

    @Override
    public User changePassword(final Long id, final String password)
    {
        final var user = getById(id);

        user.setPassword(passwordEncoder.encode(password));

        repository.update(id, user);

        return getById(id);
    }

    @Override
    public void deleteAllExpiredPasswordResetDetails()
    {
        repository.findAll().stream()
                .filter(user -> user.getPasswordResetExpiresAt() != null && user.getPasswordResetExpiresAt().isBefore(LocalDateTime.now()))
                .forEach(this::deletePasswordResetDetails);
    }

    private void deletePasswordResetDetails(final User user)
    {
        user.setPasswordResetCode(null);
        user.setPasswordResetToken(null);
        user.setPasswordResetExpiresAt(null);

        repository.update(user.getUserId(), user);
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException
    {
        final var user = Optional.ofNullable(getByUsername(username))
                .orElseGet(() -> Optional.ofNullable(getByEmail(username))
                .orElseThrow(() -> new UserNotFoundByLoginException(username)));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
}
