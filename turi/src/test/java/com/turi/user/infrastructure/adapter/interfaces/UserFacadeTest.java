package com.turi.user.infrastructure.adapter.interfaces;

import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.testhelper.annotation.RestControllerTest;
import com.turi.user.domain.exception.InvalidUserException;
import com.turi.user.domain.exception.UserNotFoundException;
import com.turi.user.domain.exception.UserUniqueEmailException;
import com.turi.user.domain.exception.UserUniqueUsernameException;
import com.turi.user.domain.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RestControllerTest
class UserFacadeTest
{
    @Autowired(required = false)
    private UserFacade facade;

    @Autowired(required = false)
    private PasswordEncoder passwordEncoder;

    @Test
    void testUser_IsUsernameExists()
    {
        final var user = mockUser();

        final var result = facade.isUsernameExists(user.getUsername());

        assertTrue(result);
    }

    @Test
    void testUser_IsUsernameExists_NotExists()
    {
        final var user = mockNewUser();

        final var result = facade.isUsernameExists(user.getUsername());

        assertFalse(result);
    }

    @Test
    void testUser_IsEmailExists()
    {
        final var user = mockUser();

        final var result = facade.isEmailExists(user.getEmail());

        assertTrue(result);
    }

    @Test
    void testUser_IsEmailExists_NotExists()
    {
        final var user = mockNewUser();

        final var result = facade.isEmailExists(user.getEmail());

        assertFalse(result);
    }

    @Test
    void testUser_CreateUser()
    {
        final var user = mockNewUser();

        final var result = facade.createUser(user);

        assertNotNull(result);
        assertThat(result).isEqualTo(user);

    }

    @ParameterizedTest
    @CsvSource({"Marek123", "marekmarek", "marek123", "Marek1", "marek123!"})
    void testUser_CreateUser_InvalidPassword(final String password)
    {
        final var user = mockNewUser();

        user.setPassword(password);

        assertThrows(BadRequestParameterException.class, () -> facade.createUser(user));
    }

    @Test
    void testUser_CreateUser_UniqueUsername()
    {
        final var user = mockNewUser();

        user.setUsername(mockUser().getUsername());

        assertThrows(UserUniqueUsernameException.class, () -> facade.createUser(user));
    }

    @Test
    void testUser_CreateUser_UniqueEmail()
    {
        final var user = mockNewUser();

        user.setEmail(mockUser().getEmail());

        assertThrows(UserUniqueEmailException.class, () -> facade.createUser(user));
    }

    @Test
    void testUser_CreateUser_WithoutRequiredUsernameField()
    {
        final var user = mockNewUser();

        user.setUsername(null);

        assertThrows(InvalidUserException.class, () -> facade.createUser(user));
    }

    @Test
    void testUser_CreateUser_WithoutRequiredEmailField()
    {
        final var user = mockNewUser();

        user.setEmail(null);

        assertThrows(InvalidUserException.class, () -> facade.createUser(user));
    }

    @Test
    void testUser_CreateUser_WithoutRequiredPasswordField()
    {
        final var user = mockNewUser();

        user.setPassword(null);

        assertThrows(BadRequestParameterException.class, () -> facade.createUser(user));
    }

    @Test
    void testUser_ChangeUsername()
    {
        final var user = mockUser();

        user.setUsername(mockNewUser().getUsername());

        final var result = facade.changeUsername(user.getUserId(), user.getUsername());

        assertNotNull(result);
        assertThat(result.getUserId()).isEqualTo(user.getUserId());
        assertThat(result.getUsername()).isEqualTo(user.getUsername());
        assertThat(result.getEmail()).isEqualTo(user.getEmail());
        assertThat(result.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    void testUser_ChangeUsername_UniqueUsername()
    {
        final var user = mockUser();

        final var result = facade.createUser(mockNewUser());

        user.setUsername(result.getUsername());

        assertThrows(UserUniqueUsernameException.class, () -> facade.changeUsername(user.getUserId(), user.getUsername()));
    }

    @Test
    void testUser_ChangeUsername_UserNotFound()
    {
        final var user = mockNewUser();

        assertThrows(UserNotFoundException.class, () -> facade.changeUsername(user.getUserId(), user.getUsername()));
    }

    @Test
    void testUser_ChangeEmail()
    {
        final var user = mockUser();

        user.setEmail(mockNewUser().getEmail());

        final var result = facade.changeEmail(user.getUserId(), user.getEmail());

        assertNotNull(result);
        assertThat(result.getUserId()).isEqualTo(user.getUserId());
        assertThat(result.getUsername()).isEqualTo(user.getUsername());
        assertThat(result.getEmail()).isEqualTo(user.getEmail());
        assertThat(result.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    void testUser_ChangeEmail_UniqueEmail()
    {
        final var user = mockUser();

        final var result = facade.createUser(mockNewUser());

        user.setEmail(result.getEmail());

        assertThrows(UserUniqueEmailException.class, () -> facade.changeEmail(user.getUserId(), user.getEmail()));
    }

    @Test
    void testUser_ChangeEmail_UserNotFound()
    {
        final var user = mockNewUser();

        assertThrows(UserNotFoundException.class, () -> facade.changeEmail(user.getUserId(), user.getEmail()));
    }

    @Test
    void testUser_ChangePassword()
    {
        final var user = mockUser();

        user.setPassword(mockNewUser().getPassword());

        final var result = facade.changePassword(user.getUserId(), user.getPassword());

        assertNotNull(result);
        assertThat(result.getUserId()).isEqualTo(user.getUserId());
        assertThat(result.getUsername()).isEqualTo(user.getUsername());
        assertThat(result.getEmail()).isEqualTo(user.getEmail());
        assertTrue(passwordEncoder.matches(user.getPassword(), result.getPassword()));
    }

    @ParameterizedTest
    @CsvSource({"Marek123", "marekmarek", "marek123", "Marek1", "marek123!"})
    void testUser_ChangePassword_InvalidPassword(final String password)
    {
        final var user = mockUser();

        assertThrows(BadRequestParameterException.class, () -> facade.changePassword(user.getUserId(), password));
    }

    @Test
    void testUser_ChangePassword_UserNotFound()
    {
        final var user = mockNewUser();

        assertThrows(UserNotFoundException.class, () -> facade.changePassword(user.getUserId(), user.getPassword()));
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
