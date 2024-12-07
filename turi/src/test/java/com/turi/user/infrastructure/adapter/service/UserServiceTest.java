package com.turi.user.infrastructure.adapter.service;

import com.turi.infrastructure.properties.SecurityProperties;
import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.testhelper.annotation.ServiceTest;
import com.turi.user.domain.exception.*;
import com.turi.user.domain.model.User;
import com.turi.user.domain.port.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ServiceTest
class UserServiceTest
{
    @Autowired
    private UserService service;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityProperties properties;

    @Test
    void testUser_GetById()
    {
        final var user = mockUser();

        final var result = service.getById(user.getUserId());

        assertNotNull(result);
        assertThat(result).isEqualTo(user);
    }

    @Test
    void testUser_GetById_UserNotFound()
    {
        assertThrows(UserNotFoundException.class, () -> service.getById(mockNewUser().getUserId()));
    }

    @Test
    void testUser_GetByUsername()
    {
        final var user = mockUser();

        final var result = service.getByUsername(user.getUsername());

        assertNotNull(result);
        assertThat(result).isEqualTo(user);
    }

    @Test
    void testUser_GetByUsername_UserNotFound()
    {
        final var user = mockNewUser();

        final var result = service.getByUsername(user.getUsername());

        assertNull(result);
    }

    @Test
    void testUser_GetByEmail()
    {
        final var user = mockUser();

        final var result = service.getByEmail(user.getEmail());

        assertNotNull(result);
        assertThat(result).isEqualTo(user);
    }

    @Test
    void testUser_GetByEmail_UserNotFound()
    {
        final var user = mockNewUser();

        final var result = service.getByEmail(user.getEmail());

        assertNull(result);
    }

    @Test
    void testUser_GetUsername()
    {
        final var user = mockUser();

        final var result = service.getUsername(user.getUserId());

        assertNotNull(result);
        assertThat(result).isEqualTo(user.getUsername());
    }

    @Test
    void testUser_GetUsername_UserNotFound()
    {
        assertThrows(UserNotFoundException.class, () -> service.getById(mockNewUser().getUserId()));
    }

    @Test
    void testUser_GetEmail()
    {
        final var user = mockUser();

        final var result = service.getEmail(user.getUserId());

        assertNotNull(result);
        assertThat(result).isEqualTo(user.getEmail());
    }

    @Test
    void testUser_GetEmail_UserNotFound()
    {
        assertThrows(UserNotFoundException.class, () -> service.getById(mockNewUser().getUserId()));
    }

    @Test
    void testUser_GetByPasswordResetToken()
    {
        final var user = mockUser();

        final var result = service.getByPasswordResetToken(user.getPasswordResetToken());

        assertNotNull(result);
        assertThat(result).isEqualTo(user);
    }

    @Test
    void testUser_GetByPasswordResetToken_UserNotFound()
    {
        final var user = mockNewUser();

        final var result = service.getByPasswordResetToken(user.getPasswordResetToken());

        assertNull(result);
    }

    @Test
    void testUser_GetUserIdByLogin_ByUsername()
    {
        final var user = mockUser();

        final var result = service.getUserIdByLogin(user.getUsername());

        assertNotNull(result);
        assertThat(result).isEqualTo(user.getUserId());
    }

    @Test
    void testUser_GetUserIdByLogin_ByEmail()
    {
        final var user = mockUser();

        final var result = service.getUserIdByLogin(user.getEmail());

        assertNotNull(result);
        assertThat(result).isEqualTo(user.getUserId());
    }

    @Test
    void testUser_GetUserIdByLogin_NotFound()
    {
        assertThrows(UserNotFoundByLoginException.class, () -> service.getUserIdByLogin(mockNewUser().getUsername()));
    }

    @Test
    void testUser_IsUsernameExists()
    {
        final var user = mockUser();

        final var result = service.isUsernameExists(user.getUsername());

        assertTrue(result);
    }

    @Test
    void testUser_IsUsernameExists_NotExists()
    {
        final var user = mockNewUser();

        final var result = service.isUsernameExists(user.getUsername());

        assertFalse(result);
    }

    @Test
    void testUser_IsEmailExists()
    {
        final var user = mockUser();

        final var result = service.isEmailExists(user.getEmail());

        assertTrue(result);
    }

    @Test
    void testUser_IsEmailExists_NotExists()
    {
        final var user = mockNewUser();

        final var result = service.isEmailExists(user.getEmail());

        assertFalse(result);
    }

    @Test
    void testUser_SendResetPasswordCode()
    {
        final var mock = mockNewUser();
        mock.setPasswordResetCode(null);
        mock.setPasswordResetToken(null);
        mock.setPasswordResetExpiresAt(null);

        final var user = service.create(mock);

        final var result = service.sendResetPasswordCode(user.getEmail());

        assertNotNull(result);
        assertNotNull(result.getToken());
        assertThat(result.getExpiresIn()).isEqualTo(properties.getAccessTokenExpirationTime());
    }

    @Test
    void testUser_SendResetPasswordCode_CodeResend()
    {
        final var user = mockUser();

        final var result = service.sendResetPasswordCode(user.getEmail());

        assertNotNull(result);
        assertNotNull(result.getToken());
        assertThat(result.getExpiresIn()).isEqualTo(properties.getAccessTokenExpirationTime());
    }

    @Test
    void testUser_SendResetPasswordCode_UserNotFound()
    {
        assertThrows(UserNotFoundByEmailException.class, () -> service.sendResetPasswordCode(mockNewUser().getEmail()));
    }

    @Test
    void testUser_SendResetPasswordCode_ResetCodeRecentlySent()
    {
        final var mock = mockNewUser();
        mock.setPasswordResetExpiresAt(LocalDateTime.now().plusMinutes(15));

        final var user = service.create(mock);

        assertThrows(UserResetCodeRecentlySentException.class, () -> service.sendResetPasswordCode(user.getEmail()));
    }

    @Test
    void testUser_ResetPassword()
    {
        final var mock = mockUser();

        service.sendResetPasswordCode(mock.getEmail());

        final var user = service.getById(mock.getUserId());

        final var result = service.resetPassword(user.getPasswordResetToken(), user.getPasswordResetCode());

        assertNotNull(result);
        assertNotNull(result.getRefreshToken());
        assertThat(result.getRefreshTokenExpiresIn()).isEqualTo(properties.getRefreshTokenExpirationTime());
    }

    @Test
    void testUser_ResetPassword_UserNotFound()
    {
        final var user = mockNewUser();

        assertThrows(UserNotFoundByResetTokenException.class, () -> service.resetPassword(user.getPasswordResetToken(), user.getPasswordResetCode()));
    }

    @Test
    void testUser_ResetPassword_InvalidResetCode()
    {
        final var mock = mockUser();

        service.sendResetPasswordCode(mock.getEmail());

        final var user = service.getById(mock.getUserId());

        assertThrows(BadRequestParameterException.class, () -> service.resetPassword(user.getPasswordResetToken(), mockNewUser().getPasswordResetCode()));
    }

    @Test
    void testUser_ResetPassword_ResetCodeExpired()
    {
        final var mock = mockNewUser();
        mock.setPasswordResetExpiresAt(LocalDateTime.now().minusMinutes(1));

        final var user = service.create(mock);

        assertThrows(UserResetCodeExpiredException.class, () -> service.resetPassword(user.getPasswordResetToken(), user.getPasswordResetCode()));
    }

    @Test
    void testUser_CreateUser()
    {
        final var user = mockNewUser();

        final var userId = service.create(user).getUserId();

        final var result = service.getById(userId);

        assertNotNull(result);
        assertThat(result).isEqualTo(user);
    }

    @Test
    void testUser_CreateUser_UniqueUsername()
    {
        final var user = mockNewUser();

        user.setUsername(mockUser().getUsername());

        assertThrows(UserUniqueUsernameException.class, () -> service.create(user));
    }

    @Test
    void testUser_CreateUser_UniqueEmail()
    {
        final var user = mockNewUser();

        user.setEmail(mockUser().getEmail());

        assertThrows(UserUniqueEmailException.class, () -> service.create(user));
    }

    @Test
    void testUser_CreateUser_WithoutRequiredUsernameField()
    {
        final var user = mockNewUser();

        user.setUsername(null);

        assertThrows(InvalidUserException.class, () -> service.create(user));
    }

    @Test
    void testUser_CreateUser_WithoutRequiredEmailField()
    {
        final var user = mockNewUser();

        user.setEmail(null);

        assertThrows(InvalidUserException.class, () -> service.create(user));
    }

    @Test
    void testUser_CreateUser_WithoutRequiredPasswordField()
    {
        final var user = mockNewUser();

        user.setPassword(null);

        assertThrows(BadRequestParameterException.class, () -> service.create(user));
    }

    @Test
    void testUser_ChangeUsername()
    {
        final var user = mockUser();

        user.setUsername(mockNewUser().getUsername());

        final var result = service.changeUsername(user.getUserId(), user.getUsername());

        assertNotNull(result);
        assertThat(result).isEqualTo(user);
    }

    @Test
    void testUser_ChangeUsername_UniqueUsername()
    {
        final var user = mockUser();

        final var result = service.create(mockNewUser());

        user.setUsername(result.getUsername());

        assertThrows(UserUniqueUsernameException.class, () -> service.changeUsername(user.getUserId(), user.getUsername()));
    }

    @Test
    void testUser_ChangeUsername_UserNotFound()
    {
        final var user = mockNewUser();

        assertThrows(UserNotFoundException.class, () -> service.changeUsername(user.getUserId(), user.getUsername()));
    }

    @Test
    void testUser_ChangeEmail()
    {
        final var user = mockUser();

        user.setEmail(mockNewUser().getEmail());

        final var result = service.changeEmail(user.getUserId(), user.getEmail());

        assertNotNull(result);
        assertThat(result).isEqualTo(user);
    }

    @Test
    void testUser_ChangeEmail_UniqueEmail()
    {
        final var user = mockUser();

        final var result = service.create(mockNewUser());

        user.setEmail(result.getEmail());

        assertThrows(UserUniqueEmailException.class, () -> service.changeEmail(user.getUserId(), user.getEmail()));
    }

    @Test
    void testUser_ChangeEmail_UserNotFound()
    {
        assertThrows(UserNotFoundException.class, () -> service.changeEmail(mockNewUser().getUserId(), "jano@turi.com"));
    }

    @Test
    void testUser_ChangePassword()
    {
        final var user = mockUser();

        user.setPassword(mockNewUser().getPassword());

        final var result = service.changePassword(user.getUserId(), user.getPassword());

        assertNotNull(result);
        assertThat(result.getUserId()).isEqualTo(user.getUserId());
        assertThat(result.getUsername()).isEqualTo(user.getUsername());
        assertThat(result.getEmail()).isEqualTo(user.getEmail());
        assertTrue(passwordEncoder.matches(user.getPassword(), result.getPassword()));
    }

    @Test
    void testUser_ChangePassword_UserNotFound()
    {
        final var user = mockNewUser();

        assertThrows(UserNotFoundException.class, () -> service.changePassword(user.getUserId(), user.getPassword()));
    }

    @Test
    void testUser_DeleteAllExpiredPasswordResetDetails()
    {
        final var mock = service.create(mockNewUser());

        final var users = List.of(mockUser(), mock);

        service.deleteAllExpiredPasswordResetDetails();

        users.forEach(user -> {
            final var result = service.getById(user.getUserId());

            assertNotNull(result);
            assertNull(result.getPasswordResetCode());
            assertNull(result.getPasswordResetToken());
            assertNull(result.getPasswordResetExpiresAt());
        });
    }

    @Test
    void testUser_DeleteAllExpiredPasswordResetDetails_NothingToDelete()
    {
        final var mock = mockUser();

        final var resetToken = service.sendResetPasswordCode(mock.getEmail());

        assertNotNull(resetToken);

        service.deleteAllExpiredPasswordResetDetails();

        final var user = service.getById(mock.getUserId());

        assertNotNull(user);
        assertNotNull(user.getPasswordResetCode());
        assertNotNull(user.getPasswordResetToken());
        assertNotNull(user.getPasswordResetExpiresAt());
    }

    @Test
    void testUser_LoadUserByUsername_ByUsername()
    {
        final var user = mockUser();

        final var result = userDetailsService.loadUserByUsername(user.getUsername());

        assertNotNull(result);
        assertThat(result.getUsername()).isEqualTo(user.getUsername());
        assertThat(result.getPassword()).isEqualTo(user.getPassword());
        assertTrue(result.isEnabled());
        assertTrue(result.isAccountNonExpired());
        assertTrue(result.isCredentialsNonExpired());
        assertTrue(result.isAccountNonLocked());
    }

    @Test
    void testUser_LoadUserByUsername_ByUsername_UserNotFound()
    {
        final var user = mockNewUser();

        assertThrows(UserNotFoundByLoginException.class, () -> userDetailsService.loadUserByUsername(user.getUsername()));
    }

    @Test
    void testUser_LoadUserByUsername_ByEmail()
    {
        final var user = mockUser();

        final var result = userDetailsService.loadUserByUsername(user.getEmail());

        assertNotNull(result);
        assertThat(result.getUsername()).isEqualTo(user.getUsername());
        assertThat(result.getPassword()).isEqualTo(user.getPassword());
        assertTrue(result.isEnabled());
        assertTrue(result.isAccountNonExpired());
        assertTrue(result.isCredentialsNonExpired());
        assertTrue(result.isAccountNonLocked());
    }

    @Test
    void testUser_LoadUserByUsername_ByEmail_UserNotFound()
    {
        final var user = mockNewUser();

        assertThrows(UserNotFoundByLoginException.class, () -> userDetailsService.loadUserByUsername(user.getEmail()));
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
