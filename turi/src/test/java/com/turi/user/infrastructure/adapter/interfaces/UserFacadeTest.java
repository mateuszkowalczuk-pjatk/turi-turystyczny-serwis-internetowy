package com.turi.user.infrastructure.adapter.interfaces;

import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.testhelper.annotation.RestControllerTest;
import com.turi.user.domain.exception.*;
import com.turi.user.domain.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static com.turi.testhelper.utils.ContextHelper.setContextUserId;
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
    void testUser_GetUserById()
    {
        final var user = mockUser();

        final var result = facade.getUserById(user.getUserId());

        assertNotNull(result);
        assertThat(result).isEqualTo(user);
    }

    @Test
    void testUser_GetUserById_UserIdIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.getUserById(null));
    }

    @Test
    void testUser_GetUserById_UserNotFound()
    {
        assertThrows(UserNotFoundException.class, () -> facade.getUserById(mockNewUser().getUserId()));
    }

    @Test
    void testUser_GetUserIdByLogin()
    {
        final var user = mockUser();

        final var result = facade.getUserIdByLogin(user.getUsername());

        assertNotNull(result);
        assertThat(result).isEqualTo(user.getUserId());
    }

    @Test
    void testUser_GetUserIdByLogin_LoginIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.getUserIdByLogin(null));
    }

    @Test
    void testUser_GetUserIdByLogin_UserNotFound()
    {
        assertThrows(UserNotFoundByLoginException.class, () -> facade.getUserIdByLogin(mockNewUser().getUsername()));
    }

    @Test
    void testUser_IsUserUsernameExists()
    {
        final var result = facade.isUserUsernameExists(mockUser().getUsername()).getBody();

        assertNotNull(result);
        assertTrue(result);
    }

    @Test
    void testUser_IsUserUsernameExists_NotExists()
    {
        final var result = facade.isUserUsernameExists(mockNewUser().getUsername()).getBody();

        assertNotNull(result);
        assertFalse(result);
    }

    @Test
    void testUser_IsUserUsernameExists_UsernameIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.isUserUsernameExists(null));
    }

    @Test
    void testUser_IsUserEmailExists()
    {
        final var result = facade.isUserEmailExists(mockUser().getEmail()).getBody();

        assertNotNull(result);
        assertTrue(result);
    }

    @Test
    void testUser_IsUserEmailExists_NotExists()
    {
        final var result = facade.isUserEmailExists(mockNewUser().getEmail()).getBody();

        assertNotNull(result);
        assertFalse(result);
    }

    @Test
    void testUser_IsUserEmailExists_EmailIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.isUserEmailExists(null));
    }

    @Test
    void testUser_SendResetUserPasswordCode()
    {
        final var result = facade.sendResetUserPasswordCode(mockUser().getEmail());

        assertNotNull(result);
        assertTrue(result.getStatusCode().is2xxSuccessful());

        final var cookie = result.getHeaders().get("Set-Cookie");

        assertNotNull(cookie);
        assertTrue(cookie.get(0).contains("resetToken="));
        assertTrue(cookie.get(0).contains("Max-Age=900000"));
        assertTrue(cookie.get(0).contains("Secure"));
        assertTrue(cookie.get(0).contains("HttpOnly"));
        assertTrue(cookie.get(0).contains("SameSite=Strict"));
    }

    @Test
    void testUser_SendResetUserPasswordCode_EmailIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.sendResetUserPasswordCode(null));
    }

    @Test
    void testUser_SendResetUserPasswordCode_UserNotFound()
    {
        assertThrows(UserNotFoundByEmailException.class, () -> facade.sendResetUserPasswordCode(mockNewUser().getEmail()));
    }

    @Test
    void testUser_SendResetUserPasswordCode_ResetCodeRecentlySent()
    {
        final var mock = mockNewUser();
        mock.setPasswordResetExpiresAt(LocalDateTime.now().plusMinutes(15));

        final var user = facade.createUser(mock);

        assertThrows(UserResetCodeRecentlySentException.class, () -> facade.sendResetUserPasswordCode(user.getEmail()));
    }

    @Test
    void testUser_ResetUserPassword()
    {
        final var mock = mockUser();

        facade.sendResetUserPasswordCode(mock.getEmail());

        final var user = facade.getUserById(mock.getUserId());

        final var result = facade.resetUserPassword(user.getPasswordResetToken(), String.valueOf(user.getPasswordResetCode()));

        assertNotNull(result);
        assertTrue(result.getStatusCode().is2xxSuccessful());

        final var cookie = result.getHeaders().get("Set-Cookie");

        assertNotNull(cookie);
        assertTrue(cookie.get(0).contains("refreshToken="));
        assertTrue(cookie.get(0).contains("Max-Age=604800"));
        assertTrue(cookie.get(0).contains("Secure"));
        assertTrue(cookie.get(0).contains("HttpOnly"));
        assertTrue(cookie.get(0).contains("SameSite=Strict"));
    }

    @Test
    void testUser_ResetUserPassword_ResetTokenIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.resetUserPassword(null, String.valueOf(mockUser().getPasswordResetCode())));
    }

    @Test
    void testUser_ResetUserPassword_CodeIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.resetUserPassword(mockUser().getPasswordResetToken(), null));
    }

    @Test
    void testUser_ResetUserPassword_UserNotFound()
    {
        final var user = mockNewUser();

        assertThrows(UserNotFoundByResetTokenException.class, () -> facade.resetUserPassword(user.getPasswordResetToken(), String.valueOf(user.getPasswordResetCode())));
    }

    @Test
    void testUser_ResetUserPassword_InvalidResetCode()
    {
        final var mock = mockUser();

        facade.sendResetUserPasswordCode(mock.getEmail());

        final var user = facade.getUserById(mock.getUserId());

        assertThrows(BadRequestParameterException.class, () -> facade.resetUserPassword(user.getPasswordResetToken(), String.valueOf(mockNewUser().getPasswordResetCode())));
    }

    @Test
    void testUser_ResetUserPassword_ResetCodeExpired()
    {
        final var mock = mockNewUser();
        mock.setPasswordResetExpiresAt(LocalDateTime.now().minusMinutes(1));

        final var user = facade.createUser(mock);

        assertThrows(UserResetCodeExpiredException.class, () -> facade.resetUserPassword(user.getPasswordResetToken(), String.valueOf(user.getPasswordResetCode())));
    }

    @Test
    void testUser_CreateUser()
    {
        final var user = mockNewUser();

        final var result = facade.createUser(user);

        assertNotNull(result);
        assertThat(result).isEqualTo(user);
    }

    @Test
    void testUser_CreateUser_UserIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.createUser(null));
    }

    @ParameterizedTest
    @CsvSource({"marek@", "@", "marek", "@marek", "@marek@"})
    void testUser_CreateUser_InvalidEmail(final String email)
    {
        final var user = mockNewUser();

        user.setEmail(email);

        assertThrows(BadRequestParameterException.class, () -> facade.createUser(user));
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
    void testUser_ChangeUserUsername()
    {
        final var user = mockUser();

        user.setUsername(mockNewUser().getUsername());

        setContextUserId(user.getUserId());

        final var result = facade.changeUserUsername(user.getUsername()).getBody();

        assertNotNull(result);
        assertThat(result.getUserId()).isEqualTo(user.getUserId());
        assertThat(result.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    void testUser_ChangeUserUsername_UsernameIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.changeUserUsername(null));
    }

    @Test
    void testUser_ChangeUserUsername_ContextUserIdIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.changeUserUsername(mockNewUser().getUsername()));
    }

    @Test
    void testUser_ChangeUserUsername_UniqueUsername()
    {
        final var user = mockUser();

        final var result = facade.createUser(mockNewUser());

        user.setUsername(result.getUsername());

        setContextUserId(user.getUserId());

        assertThrows(UserUniqueUsernameException.class, () -> facade.changeUserUsername(user.getUsername()));
    }

    @Test
    void testUser_ChangeUserUsername_UserNotFound()
    {
        final var user = mockNewUser();

        setContextUserId(user.getUserId());

        assertThrows(UserNotFoundException.class, () -> facade.changeUserUsername(user.getUsername()));
    }

    @Test
    void testUser_ChangeUserEmail()
    {
        final var user = mockUser();

        user.setEmail(mockNewUser().getEmail());

        setContextUserId(user.getUserId());

        final var result = facade.changeUserEmail(user.getEmail()).getBody();

        assertNotNull(result);
        assertThat(result.getUserId()).isEqualTo(user.getUserId());
        assertThat(result.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void testUser_ChangeUserEmail_EmailIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.changeUserEmail(null));
    }

    @Test
    void testUser_ChangeUserEmail_ContextUserIdIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.changeUserEmail(mockNewUser().getEmail()));
    }

    @ParameterizedTest
    @CsvSource({"marek@", "@", "marek", "@marek", "@marek@"})
    void testUser_ChangeUserEmail_InvalidEmail(final String email)
    {
        assertThrows(BadRequestParameterException.class, () -> facade.changeUserEmail(email));
    }

    @Test
    void testUser_ChangeUserEmail_UniqueEmail()
    {
        final var user = mockUser();

        final var result = facade.createUser(mockNewUser());

        user.setEmail(result.getEmail());

        setContextUserId(user.getUserId());

        assertThrows(UserUniqueEmailException.class, () -> facade.changeUserEmail(user.getEmail()));
    }

    @Test
    void testUser_ChangeUserEmail_UserNotFound()
    {
        final var user = mockNewUser();

        setContextUserId(user.getUserId());

        assertThrows(UserNotFoundException.class, () -> facade.changeUserEmail(user.getEmail()));
    }

    @Test
    void testUser_ChangeUserPassword()
    {
        final var user = mockUser();

        user.setPassword(mockNewUser().getPassword());

        setContextUserId(user.getUserId());

        final var result = facade.changeUserPassword(user.getPassword()).getBody();

        assertNotNull(result);
        assertThat(result.getUserId()).isEqualTo(user.getUserId());
        assertTrue(passwordEncoder.matches(user.getPassword(), result.getPassword()));
    }

    @Test
    void testUser_ChangeUserPassword_PasswordIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.changeUserPassword(null));
    }

    @Test
    void testUser_ChangeUserPassword_ContextUserIdIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.changeUserPassword(mockNewUser().getPassword()));
    }

    @ParameterizedTest
    @CsvSource({"Marek123", "marekmarek", "marek123", "Marek1", "marek123!"})
    void testUser_ChangeUserPassword_InvalidPassword(final String password)
    {
        assertThrows(BadRequestParameterException.class, () -> facade.changeUserPassword(password));
    }

    @Test
    void testUser_ChangeUserPassword_UserNotFound()
    {
        final var user = mockNewUser();

        setContextUserId(user.getUserId());

        assertThrows(UserNotFoundException.class, () -> facade.changeUserPassword(user.getPassword()));
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
