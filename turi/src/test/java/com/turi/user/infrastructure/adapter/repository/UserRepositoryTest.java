package com.turi.user.infrastructure.adapter.repository;

import com.turi.testhelper.annotation.RepositoryTest;
import com.turi.user.domain.exception.InvalidUserException;
import com.turi.user.domain.exception.UserNotFoundException;
import com.turi.user.domain.model.User;
import com.turi.user.domain.port.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RepositoryTest
class UserRepositoryTest
{
    @Autowired
    private UserRepository repository;

    @Test
    void testUser_FindAll()
    {
        final var result = repository.findAll();

        final var user = mockUser();

        assertNotNull(result);
        assertThat(result).isEqualTo(List.of(user));
    }

    @Test
    void testUser_FindAll_NothingFound()
    {
        final var users = repository.findAll();

        users.forEach(user -> repository.deleteById(user.getUserId()));

        final var result = repository.findAll();

        assertThat(result).isEmpty();
    }

    @Test
    void testUser_FindById()
    {
        final var user = mockUser();

        final var result = repository.findById(user.getUserId());

        assertNotNull(result);
        assertThat(result).isEqualTo(user);
    }

    @Test
    void testUser_FindById_NotFound()
    {
        assertThrows(UserNotFoundException.class, () -> repository.findById(mockNewUser().getUserId()));
    }

    @Test
    void testUser_FindByUsername()
    {
        final var user = mockUser();

        final var result = repository.findByUsername(user.getUsername());

        assertNotNull(result);
        assertThat(result).isEqualTo(user);
    }

    @Test
    void testUser_FindByUsername_NotFound()
    {
        final var result = repository.findByUsername(mockNewUser().getUsername());

        assertNull(result);
    }

    @Test
    void testUser_FindByEmail()
    {
        final var user = mockUser();

        final var result = repository.findByEmail(user.getEmail());

        assertNotNull(result);
        assertThat(result).isEqualTo(user);
    }

    @Test
    void testUser_FindByEmail_NotFound()
    {
        final var user = mockNewUser();

        final var result = repository.findByEmail(user.getEmail());

        assertNull(result);
    }

    @Test
    void testUser_FindByPasswordResetToken()
    {
        final var user = mockUser();

        final var result = repository.findByPasswordResetToken(user.getPasswordResetToken());

        assertNotNull(result);
        assertThat(result).isEqualTo(user);
    }

    @Test
    void testUser_FindByPasswordResetToken_NotFound()
    {
        final var user = mockNewUser();

        final var result = repository.findByPasswordResetToken(user.getPasswordResetToken());

        assertNull(result);
    }

    @Test
    void testUser_Insert()
    {
        final var user = mockNewUser();

        final var userId = repository.insert(user);

        user.setUserId(userId);

        final var result = repository.findById(userId);

        assertNotNull(result);
        assertThat(result).isEqualTo(user);
    }

    @Test
    void testUser_Insert_WithoutRequiredUsernameField()
    {
        final var user = mockNewUser();

        user.setUsername(null);

        assertThrows(InvalidUserException.class, () -> repository.insert(user));
    }

    @Test
    void testUser_Insert_WithoutRequiredEmailField()
    {
        final var user = mockNewUser();

        user.setEmail(null);

        assertThrows(InvalidUserException.class, () -> repository.insert(user));
    }

    @Test
    void testUser_Insert_WithoutRequiredPasswordField()
    {
        final var user = mockNewUser();

        user.setPassword(null);

        assertThrows(InvalidUserException.class, () -> repository.insert(user));
    }

    @Test
    void testUser_Update()
    {
        final var user = mockUser();

        user.setUsername("Jan");

        repository.update(user.getUserId(), user);

        final var result = repository.findById(user.getUserId());

        assertNotNull(result);
        assertThat(result).isEqualTo(user);
    }

    @Test
    void testUser_Update_UserNotFound()
    {
        final var user = mockNewUser();

        repository.update(user.getUserId(), user);

        assertThrows(UserNotFoundException.class, () -> repository.findById(user.getUserId()));
    }

    @Test
    void testUser_Update_WithoutRequiredUsernameField()
    {
        final var user = mockUser();

        user.setUsername(null);

        assertThrows(InvalidUserException.class, () -> repository.update(user.getUserId(), user));
    }

    @Test
    void testUser_Update_WithoutRequiredEmailField()
    {
        final var user = mockUser();

        user.setEmail(null);

        assertThrows(InvalidUserException.class, () -> repository.update(user.getUserId(), user));
    }

    @Test
    void testUser_Update_WithoutRequiredPasswordField()
    {
        final var user = mockUser();

        user.setPassword(null);

        assertThrows(InvalidUserException.class, () -> repository.update(user.getUserId(), user));
    }

    @Test
    void testUser_DeleteById()
    {
        final var user = mockUser();

        repository.deleteById(user.getUserId());

        assertThrows(UserNotFoundException.class, () -> repository.findById(user.getUserId()));
    }

    @Test
    void testUser_DeleteById_NothingToDelete()
    {
        final var user = mockNewUser();

        assertThrows(UserNotFoundException.class, () -> repository.deleteById(user.getUserId()));
    }

    private User mockUser()
    {
        return User.builder()
                .withUserId(1L)
                .withUsername("Janek")
                .withEmail("jan@turi.com")
                .withPassword("JanKowalski123!")
                .withPasswordResetCode(123456)
                .withPasswordResetToken("sample-password-reset-token")
                .withPasswordResetExpiresAt(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .build();
    }

    private User mockNewUser()
    {
        return User.builder()
                .withUserId(2L)
                .withUsername("Marek")
                .withEmail("marek@turi.com")
                .withPassword("MarekNowak123!")
                .withPasswordResetCode(999999)
                .withPasswordResetToken("password-reset-token")
                .withPasswordResetExpiresAt(LocalDateTime.of(2024, 2, 2, 0, 0, 0))
                .build();
    }
}
