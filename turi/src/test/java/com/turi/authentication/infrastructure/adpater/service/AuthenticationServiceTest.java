package com.turi.authentication.infrastructure.adpater.service;

import com.turi.account.domain.model.AccountType;
import com.turi.account.domain.port.AccountService;
import com.turi.authentication.domain.exception.InvalidLoginException;
import com.turi.authentication.domain.exception.InvalidPasswordForLoginException;
import com.turi.authentication.domain.exception.RefreshTokenExpiredException;
import com.turi.authentication.domain.exception.RefreshTokenNotFoundByTokenException;
import com.turi.authentication.domain.model.LoginParam;
import com.turi.authentication.domain.model.LogoutParam;
import com.turi.authentication.domain.model.RefreshParam;
import com.turi.authentication.domain.model.RegisterParam;
import com.turi.authentication.domain.port.AuthenticationService;
import com.turi.authentication.domain.port.RefreshTokenService;
import com.turi.infrastructure.config.SecurityProperties;
import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.testhelper.annotation.ServiceTest;
import com.turi.user.domain.exception.InvalidUserException;
import com.turi.user.domain.exception.UserUniqueEmailException;
import com.turi.user.domain.exception.UserUniqueUsernameException;
import com.turi.user.domain.model.User;
import com.turi.user.domain.port.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ServiceTest
class AuthenticationServiceTest
{
    @Autowired
    private AuthenticationService service;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private SecurityProperties properties;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testAuthentication_Register()
    {
        final var params = mockRegisterParams();

        final var result = service.register(params);

        assertNotNull(result);
        assertNotNull(result.getAccessToken());
        assertThat(result.getRefreshTokenExpiresIn()).isEqualTo(properties.getAccessTokenExpirationTime());

        final var user = userService.getByUsername(params.getUsername());

        assertNotNull(user);
        assertThat(user.getUsername()).isEqualTo(params.getUsername());
        assertThat(user.getEmail()).isEqualTo(params.getEmail());
        assertTrue(passwordEncoder.matches(params.getPassword(), user.getPassword()));

        final var account = accountService.getByUserId(user.getUserId());

        assertNotNull(account);
        assertThat(account.getUserId()).isEqualTo(user.getUserId());
        assertThat(account.getAccountType()).isEqualTo(AccountType.INACTIVE);
        assertNotNull(account.getActivationCode());
        assertNotNull(account.getActivationCodeExpiresAt());
    }

    @Test
    void testAuthentication_Register_WithoutRequiredUsernameField()
    {
        final var params = mockRegisterParams();

        params.setUsername(null);

        assertThrows(InvalidUserException.class, () -> service.register(params));
    }

    @Test
    void testAuthentication_Register_WithoutRequiredEmailField()
    {
        final var params = mockRegisterParams();

        params.setEmail(null);

        assertThrows(BadRequestParameterException.class, () -> service.register(params));
    }

    @Test
    void testAuthentication_Register_WithoutRequiredPasswordField()
    {
        final var params = mockRegisterParams();

        params.setPassword(null);

        assertThrows(BadRequestParameterException.class, () -> service.register(params));
    }

    @Test
    void testAuthentication_Register_UniqueUsername()
    {
        final var params = mockRegisterParams();

        params.setUsername(mockUser().getUsername());

        assertThrows(UserUniqueUsernameException.class, () -> service.register(params));
    }

    @Test
    void testAuthentication_Register_UniqueEmail()
    {
        final var params = mockRegisterParams();

        params.setEmail(mockUser().getEmail());

        assertThrows(UserUniqueEmailException.class, () -> service.register(params));
    }

    @ParameterizedTest
    @CsvSource({"marek@", "@", "marek", "@marek", "@marek@"})
    void testAuthentication_Register_InvalidEmail(final String email)
    {
        final var params = mockRegisterParams();

        params.setPassword(email);

        assertThrows(BadRequestParameterException.class, () -> service.register(params));
    }

    @ParameterizedTest
    @CsvSource({"Marek123", "marekmarek", "marek123", "Marek1", "marek123!"})
    void testAuthentication_Register_InvalidPassword(final String password)
    {
        final var params = mockRegisterParams();

        params.setPassword(password);

        assertThrows(BadRequestParameterException.class, () -> service.register(params));
    }

    @Test
    void testAuthentication_login_ByUsername()
    {
        final var params = mockRegisterParams();

        service.register(params);

        accountService.activate(2L, accountService.getById(2L).getActivationCode());

        final var authParams = LoginParam.builder()
                .withLogin(params.getUsername())
                .withPassword(params.getPassword())
                .build();

        final var result = service.login(authParams);

        assertNotNull(result);
        assertNotNull(result.getAccessToken());
        assertNotNull(result.getRefreshToken());
        assertThat(result.getAccessTokenExpiresIn()).isEqualTo(properties.getAccessTokenExpirationTime());
        assertThat(result.getRefreshTokenExpiresIn()).isEqualTo(properties.getRefreshTokenExpirationTime());
    }

    @Test
    void testAuthentication_login_ByUsername_UserNotFound()
    {
        final var params = mockRegisterParams();

        final var authParams = LoginParam.builder()
                .withLogin(params.getUsername())
                .withPassword(params.getPassword())
                .build();

        assertThrows(InvalidLoginException.class, () -> service.login(authParams));
    }

    @Test
    void testAuthentication_login_ByUsername_WrongPassword()
    {
        final var params = mockRegisterParams();

        service.register(params);

        final var authenticationParams = LoginParam.builder()
                .withLogin(params.getUsername())
                .withPassword(mockUser().getPassword())
                .build();

        assertThrows(InvalidPasswordForLoginException.class, () -> service.login(authenticationParams));
    }

    @Test
    void testAuthentication_login_ByEmail()
    {
        final var params = mockRegisterParams();

        service.register(params);

        accountService.activate(2L, accountService.getById(2L).getActivationCode());

        final var authParams = LoginParam.builder()
                .withLogin(params.getEmail())
                .withPassword(params.getPassword())
                .build();

        final var result = service.login(authParams);

        assertNotNull(result);
        assertNotNull(result.getAccessToken());
        assertNotNull(result.getRefreshToken());
        assertThat(result.getAccessTokenExpiresIn()).isEqualTo(properties.getAccessTokenExpirationTime());
        assertThat(result.getRefreshTokenExpiresIn()).isEqualTo(properties.getRefreshTokenExpirationTime());
    }

    @Test
    void testAuthentication_login_ByEmail_UserNotFound()
    {
        final var params = mockRegisterParams();

        final var authenticationParams = LoginParam.builder()
                .withLogin(params.getEmail())
                .withPassword(params.getPassword())
                .build();

        assertThrows(InvalidLoginException.class, () -> service.login(authenticationParams));
    }

    @Test
    void testAuthentication_login_ByEmail_WrongPassword()
    {
        final var params = mockRegisterParams();

        service.register(params);

        final var authenticationParams = LoginParam.builder()
                .withLogin(params.getEmail())
                .withPassword(mockUser().getPassword())
                .build();

        assertThrows(InvalidPasswordForLoginException.class, () -> service.login(authenticationParams));
    }

    @Test
    void testAuthentication_Refresh()
    {
        final var params = mockRegisterParams();

        service.register(params);

        accountService.activate(2L, accountService.getById(2L).getActivationCode());

        final var authParams = LoginParam.builder()
                .withLogin(params.getUsername())
                .withPassword(params.getPassword())
                .build();

        final var authentication = service.login(authParams);

        final var refreshParams = RefreshParam.builder()
                .withRefreshToken(authentication.getRefreshToken())
                .build();

        final var result = service.refresh(refreshParams);

        assertNotNull(result);
        assertNotNull(result.getAccessToken());
        assertThat(result.getAccessTokenExpiresIn()).isEqualTo(properties.getAccessTokenExpirationTime());
    }

    @Test
    void testAuthentication_Refresh_RefreshTokenExpired()
    {
        final var params = RefreshParam.builder()
                .withRefreshToken("sample-refresh-token")
                .build();

        final var refreshParams = RefreshParam.builder()
                .withRefreshToken(params.getRefreshToken())
                .build();

        assertThrows(RefreshTokenExpiredException.class, () -> service.refresh(refreshParams));
    }

    @Test
    void testAuthentication_Refresh_RefreshTokenNotFound()
    {
        final var refreshParams = RefreshParam.builder()
                .withRefreshToken("other-refresh-token")
                .build();

        assertThrows(RefreshTokenNotFoundByTokenException.class, () -> service.refresh(refreshParams));
    }

    @Test
    void testAuthentication_Refresh_RefreshTokenIsNull()
    {
        final var refreshParams = RefreshParam.builder()
                .withRefreshToken(null)
                .build();

        assertThrows(BadRequestParameterException.class, () -> service.refresh(refreshParams));
    }

    @Test
    void testAuthentication_Logout()
    {
        final var params = mockRegisterParams();

        service.register(params);

        accountService.activate(2L, accountService.getById(2L).getActivationCode());

        final var authParams = LoginParam.builder()
                .withLogin(params.getUsername())
                .withPassword(params.getPassword())
                .build();

        final var authentication = service.login(authParams);

        final var logoutParams = LogoutParam.builder()
                .withRefreshToken(authentication.getRefreshToken())
                .build();

        service.logout(logoutParams);

        assertNull(refreshTokenService.getByToken(authentication.getRefreshToken()));
    }

    @Test
    void testAuthentication_Logout_RefreshTokenExpired()
    {
        final var logoutParams = LogoutParam.builder()
                .withRefreshToken("other-refresh-token")
                .build();

        assertThrows(RefreshTokenExpiredException.class, () -> service.logout(logoutParams));
    }

    @Test
    void testAuthentication_Logout_RefreshTokenIsNull()
    {
        final var logoutParams = LogoutParam.builder()
                .withRefreshToken(null)
                .build();

        assertThrows(BadRequestParameterException.class, () -> service.logout(logoutParams));
    }

    private RegisterParam mockRegisterParams()
    {
        return RegisterParam.builder()
                .withUsername("Marek")
                .withEmail("marek@turi.com")
                .withPassword("MarekNowak123!")
                .build();
    }

    private User mockUser()
    {
        return User.builder()
                .withUsername("Janek")
                .withEmail("jan@turi.com")
                .withPassword("JanKowalski123!")
                .build();
    }
}
