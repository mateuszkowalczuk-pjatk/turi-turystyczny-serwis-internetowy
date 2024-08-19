package com.turi.user.infrastructure.adapter.service;

import com.turi.account.domain.model.AccountType;
import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.testhelper.annotation.ServiceTest;
import com.turi.user.domain.exception.*;
import com.turi.user.domain.model.User;
import com.turi.user.domain.port.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    void testUser_GetById_IdIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> service.getById(null));
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
    void testUser_CreateUser()
    {
        final var user = mockNewUser();

        final var userId = service.createUser(user).getUserId();

        final var result = service.getById(userId);

        assertNotNull(result);
        assertThat(result).isEqualTo(user);
    }

    @Test
    void testUser_CreateUser_UniqueUsername()
    {
        final var user = mockNewUser();

        user.setUsername(mockUser().getUsername());

        assertThrows(UserUniqueUsernameException.class, () -> service.createUser(user));
    }

    @Test
    void testUser_CreateUser_UniqueEmail()
    {
        final var user = mockNewUser();

        user.setEmail(mockUser().getEmail());

        assertThrows(UserUniqueEmailException.class, () -> service.createUser(user));
    }

    @Test
    void testUser_CreateUser_WithoutRequiredUsernameField()
    {
        final var user = mockNewUser();

        user.setUsername(null);

        assertThrows(InvalidUserException.class, () -> service.createUser(user));
    }

    @Test
    void testUser_CreateUser_WithoutRequiredEmailField()
    {
        final var user = mockNewUser();

        user.setEmail(null);

        assertThrows(InvalidUserException.class, () -> service.createUser(user));
    }

    @Test
    void testUser_CreateUser_WithoutRequiredPasswordField()
    {
        final var user = mockNewUser();

        user.setPassword(null);

        assertThrows(BadRequestParameterException.class, () -> service.createUser(user));
    }

    @Test
    void testUser_ChangeUsername()
    {
        final var user = mockUser();

        user.setUsername(mockNewUser().getUsername());

        final var result = service.changeUsername(user.getUserId(), user.getUsername());

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

        final var result = service.createUser(mockNewUser());

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
        assertThat(result.getUserId()).isEqualTo(user.getUserId());
        assertThat(result.getUsername()).isEqualTo(user.getUsername());
        assertThat(result.getEmail()).isEqualTo(user.getEmail());
        assertThat(result.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    void testUser_ChangeEmail_UniqueEmail()
    {
        final var user = mockUser();

        final var result = service.createUser(mockNewUser());

        user.setEmail(result.getEmail());

        assertThrows(UserUniqueEmailException.class, () -> service.changeEmail(user.getUserId(), user.getEmail()));
    }

    @Test
    void testUser_ChangeEmail_UserNotFound()
    {
        assertThrows(UserNotFoundException.class, () -> service.changeEmail(mockNewUser().getUserId(), "jano@gmail.com"));
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
    void testUser_LoadUserByUsername_ByUsername()
    {
        final var user = mockUser();

        final var result = userDetailsService.loadUserByUsername(user.getUsername());

        assertNotNull(result);
        assertThat(result.getUsername()).isEqualTo(user.getUsername());
        assertThat(result.getPassword()).isEqualTo(user.getPassword());
        assertThat(result.getAuthorities()).extracting(GrantedAuthority::getAuthority).containsExactly(AccountType.NORMAL.name());
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
        assertThat(result.getAuthorities()).extracting(GrantedAuthority::getAuthority).containsExactly(AccountType.NORMAL.name());
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
