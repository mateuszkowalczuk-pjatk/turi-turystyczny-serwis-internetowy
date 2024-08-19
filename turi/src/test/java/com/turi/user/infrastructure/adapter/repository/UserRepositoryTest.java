package com.turi.user.infrastructure.adapter.repository;

import com.turi.testhelper.annotation.RepositoryTest;
import com.turi.user.domain.exception.InvalidUserException;
import com.turi.user.domain.exception.UserNotFoundException;
import com.turi.user.domain.model.User;
import com.turi.user.domain.port.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RepositoryTest
class UserRepositoryTest
{
    @Autowired
    private UserRepository repository;

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
        final var user = mockNewUser();

        assertThrows(UserNotFoundException.class, () -> repository.findById(user.getUserId()));
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
        final var user = mockNewUser();

        final var result = repository.findByUsername(user.getUsername());

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
    void testUser_Insert()
    {
        final var user = mockNewUser();

        final var userId = repository.insert(user);

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
    void testUser_Delete()
    {
        final var user = mockUser();

        repository.delete(user.getUserId());

        assertThrows(UserNotFoundException.class, () -> repository.findById(user.getUserId()));
    }

    @Test
    void testUser_Delete_NothingToDelete()
    {
        final var user = mockNewUser();

        assertThrows(UserNotFoundException.class, () -> repository.delete(user.getUserId()));
    }

    private User mockUser()
    {
        return User.builder()
                .withUserId(1L)
                .withUsername("Janek")
                .withEmail("jan@gmail.com")
                .withPassword("JanKowalski123!")
                .build();
    }

    private User mockNewUser()
    {
        return User.builder()
                .withUserId(2L)
                .withUsername("Marek")
                .withEmail("marek@gmail.com")
                .withPassword("MarekNowak123!")
                .build();
    }
}
