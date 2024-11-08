package com.turi.authentication.infrastructure.adpater.interfaces;

import com.turi.account.domain.model.AccountType;
import com.turi.account.domain.port.AccountService;
import com.turi.authentication.domain.exception.InvalidLoginException;
import com.turi.authentication.domain.exception.InvalidPasswordForLoginException;
import com.turi.authentication.domain.exception.RefreshTokenExpiredException;
import com.turi.authentication.domain.exception.RefreshTokenNotFoundByTokenException;
import com.turi.authentication.domain.port.RefreshTokenService;
import com.turi.authentication.infrastructure.adapter.application.queries.authentication.AuthenticationParam;
import com.turi.authentication.infrastructure.adapter.application.queries.logout.LogoutParam;
import com.turi.authentication.infrastructure.adapter.application.queries.refresh.RefreshParam;
import com.turi.authentication.infrastructure.adapter.application.queries.registration.RegistrationParam;
import com.turi.authentication.infrastructure.adapter.interfaces.AuthenticationFacade;
import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.testhelper.annotation.RestControllerTest;
import com.turi.user.domain.exception.UserUniqueEmailException;
import com.turi.user.domain.exception.UserUniqueUsernameException;
import com.turi.user.domain.model.User;
import com.turi.user.domain.port.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RestControllerTest
class AuthenticationFacadeTest
{
    @Autowired(required = false)
    private AuthenticationFacade facade;

    @Autowired(required = false)
    private UserService userService;

    @Autowired(required = false)
    private AccountService accountService;

    @Autowired(required = false)
    private RefreshTokenService refreshTokenService;

    @Autowired(required = false)
    private PasswordEncoder passwordEncoder;

    @Test
    void testAuthentication_Register()
    {
        final var params = mockRegisterParams();

        final var result = facade.register(params);

        assertNotNull(result);
        assertTrue(result.getStatusCode().is2xxSuccessful());

        final var cookie = result.getHeaders().get("Set-Cookie");

        assertNotNull(cookie);
        assertTrue(cookie.get(0).contains("activateToken="));
        assertTrue(cookie.get(0).contains("Max-Age=900000"));
        assertTrue(cookie.get(0).contains("Secure"));
        assertTrue(cookie.get(0).contains("HttpOnly"));
        assertTrue(cookie.get(0).contains("SameSite=Strict"));

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

        assertThrows(BadRequestParameterException.class, () -> facade.register(params));
    }

    @Test
    void testAuthentication_Register_WithoutRequiredEmailField()
    {
        final var params = mockRegisterParams();

        params.setEmail(null);

        assertThrows(BadRequestParameterException.class, () -> facade.register(params));
    }

    @Test
    void testAuthentication_Register_WithoutRequiredPasswordField()
    {
        final var params = mockRegisterParams();

        params.setPassword(null);

        assertThrows(BadRequestParameterException.class, () -> facade.register(params));
    }

    @Test
    void testAuthentication_Register_UniqueUsername()
    {
        final var params = mockRegisterParams();

        params.setUsername(mockUser().getUsername());

        assertThrows(UserUniqueUsernameException.class, () -> facade.register(params));
    }

    @Test
    void testAuthentication_Register_UniqueEmail()
    {
        final var params = mockRegisterParams();

        params.setEmail(mockUser().getEmail());

        assertThrows(UserUniqueEmailException.class, () -> facade.register(params));
    }

    @ParameterizedTest
    @CsvSource({"marek@", "@", "marek", "@marek", "@marek@"})
    void testAuthentication_Register_InvalidEmail(final String email)
    {
        final var params = mockRegisterParams();

        params.setPassword(email);

        assertThrows(BadRequestParameterException.class, () -> facade.register(params));
    }

    @ParameterizedTest
    @CsvSource({"Marek123", "marekmarek", "marek123", "Marek1", "marek123!"})
    void testAuthentication_Register_InvalidPassword(final String password)
    {
        final var params = mockRegisterParams();

        params.setPassword(password);

        assertThrows(BadRequestParameterException.class, () -> facade.register(params));
    }

    @Test
    void testAuthentication_Authenticate_ByUsername()
    {
        final var params = mockRegisterParams();

        facade.register(params);

        accountService.activate(2L, accountService.getById(2L).getActivationCode());

        final var authParams = AuthenticationParam.builder()
                .withLogin(params.getUsername())
                .withPassword(params.getPassword())
                .build();

        final var result = facade.authenticate(authParams);

        assertNotNull(result);
        assertTrue(result.getStatusCode().is2xxSuccessful());

        final var cookie = result.getHeaders().get("Set-Cookie");

        assertNotNull(cookie);

        assertTrue(cookie.get(0).contains("accessToken="));
        assertTrue(cookie.get(0).contains("Max-Age=900000"));
        assertTrue(cookie.get(0).contains("Secure"));
        assertTrue(cookie.get(0).contains("HttpOnly"));
        assertTrue(cookie.get(0).contains("SameSite=Strict"));

        assertTrue(cookie.get(1).contains("refreshToken="));
        assertTrue(cookie.get(1).contains("Max-Age=604800"));
        assertTrue(cookie.get(1).contains("Secure"));
        assertTrue(cookie.get(1).contains("HttpOnly"));
        assertTrue(cookie.get(1).contains("SameSite=Strict"));
    }

    @Test
    void testAuthentication_Authenticate_ByUsername_UserNotFound()
    {
        final var params = mockRegisterParams();

        final var authParams = AuthenticationParam.builder()
                .withLogin(params.getUsername())
                .withPassword(params.getPassword())
                .build();

        assertThrows(InvalidLoginException.class, () -> facade.authenticate(authParams));
    }

    @Test
    void testAuthentication_Authenticate_ByUsername_WrongPassword()
    {
        final var params = mockRegisterParams();

        facade.register(params);

        final var authenticationParams = AuthenticationParam.builder()
                .withLogin(params.getUsername())
                .withPassword(mockUser().getPassword())
                .build();

        assertThrows(InvalidPasswordForLoginException.class, () -> facade.authenticate(authenticationParams));
    }

    @Test
    void testAuthentication_Authenticate_ByEmail()
    {
        final var params = mockRegisterParams();

        facade.register(params);

        accountService.activate(2L, accountService.getById(2L).getActivationCode());

        final var authParams = AuthenticationParam.builder()
                .withLogin(params.getEmail())
                .withPassword(params.getPassword())
                .build();

        final var result = facade.authenticate(authParams);

        assertNotNull(result);
        assertTrue(result.getStatusCode().is2xxSuccessful());

        final var cookie = result.getHeaders().get("Set-Cookie");

        assertNotNull(cookie);

        assertTrue(cookie.get(0).contains("accessToken="));
        assertTrue(cookie.get(0).contains("Max-Age=900000"));
        assertTrue(cookie.get(0).contains("Secure"));
        assertTrue(cookie.get(0).contains("HttpOnly"));
        assertTrue(cookie.get(0).contains("SameSite=Strict"));

        assertTrue(cookie.get(1).contains("refreshToken="));
        assertTrue(cookie.get(1).contains("Max-Age=604800"));
        assertTrue(cookie.get(1).contains("Secure"));
        assertTrue(cookie.get(1).contains("HttpOnly"));
        assertTrue(cookie.get(1).contains("SameSite=Strict"));
    }

    @Test
    void testAuthentication_Authenticate_ByEmail_UserNotFound()
    {
        final var params = mockRegisterParams();

        final var authenticationParams = AuthenticationParam.builder()
                .withLogin(params.getEmail())
                .withPassword(params.getPassword())
                .build();

        assertThrows(InvalidLoginException.class, () -> facade.authenticate(authenticationParams));
    }

    @Test
    void testAuthentication_Authenticate_ByEmail_WrongPassword()
    {
        final var params = mockRegisterParams();

        facade.register(params);

        final var authenticationParams = AuthenticationParam.builder()
                .withLogin(params.getEmail())
                .withPassword(mockUser().getPassword())
                .build();

        assertThrows(InvalidPasswordForLoginException.class, () -> facade.authenticate(authenticationParams));
    }

    @Test
    void testAuthentication_Authenticate_WithoutRequiredLoginField()
    {
        final var authenticationParams = AuthenticationParam.builder()
                .withLogin(null)
                .withPassword(mockUser().getPassword())
                .build();

        assertThrows(BadRequestParameterException.class, () -> facade.authenticate(authenticationParams));
    }

    @Test
    void testAuthentication_Authenticate_WithoutRequiredPasswordField()
    {
        final var authenticationParams = AuthenticationParam.builder()
                .withLogin(mockUser().getUsername())
                .withPassword(null)
                .build();

        assertThrows(BadRequestParameterException.class, () -> facade.authenticate(authenticationParams));
    }

    @Test
    void testAuthentication_Refresh()
    {
        final var params = mockRegisterParams();

        facade.register(params);

        accountService.activate(2L, accountService.getById(2L).getActivationCode());

        final var authParams = AuthenticationParam.builder()
                .withLogin(params.getUsername())
                .withPassword(params.getPassword())
                .build();

        final var authentication = facade.authenticate(authParams);

        final var cookie = authentication.getHeaders().get("Set-Cookie");

        assertNotNull(cookie);

        final var refreshToken = cookie.get(1).split("Token=")[1].split(";")[0];

        final var refreshParams = RefreshParam.builder()
                .withRefreshToken(refreshToken)
                .build();

        final var result = facade.refresh(refreshParams);

        final var resultCookie = result.getHeaders().get("Set-Cookie");

        assertNotNull(resultCookie);

        assertTrue(resultCookie.get(0).contains("accessToken="));
        assertTrue(resultCookie.get(0).contains("Max-Age=900000"));
        assertTrue(resultCookie.get(0).contains("Secure"));
        assertTrue(resultCookie.get(0).contains("HttpOnly"));
        assertTrue(resultCookie.get(0).contains("SameSite=Strict"));
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

        assertThrows(RefreshTokenExpiredException.class, () -> facade.refresh(refreshParams));
    }

    @Test
    void testAuthentication_Refresh_RefreshTokenNotFound()
    {
        final var refreshParams = RefreshParam.builder()
                .withRefreshToken("other-refresh-token")
                .build();

        assertThrows(RefreshTokenNotFoundByTokenException.class, () -> facade.refresh(refreshParams));
    }

    @Test
    void testAuthentication_Refresh_WithoutRequiredRefreshTokenField()
    {
        final var refreshParams = RefreshParam.builder()
                .withRefreshToken(null)
                .build();

        assertThrows(BadRequestParameterException.class, () -> facade.refresh(refreshParams));
    }

    @Test
    void testAuthentication_Logout()
    {
        final var params = mockRegisterParams();

        facade.register(params);

        accountService.activate(2L, accountService.getById(2L).getActivationCode());

        final var authParams = AuthenticationParam.builder()
                .withLogin(params.getUsername())
                .withPassword(params.getPassword())
                .build();

        final var authentication = facade.authenticate(authParams);

        final var cookie = authentication.getHeaders().get("Set-Cookie");

        assertNotNull(cookie);

        final var refreshToken = cookie.get(1).split("Token=")[1].split(";")[0];


        final var response = Mockito.mock(HttpServletResponse.class);

        final var logoutParams = LogoutParam.builder()
                .withRefreshToken(refreshToken)
                .withResponse(response)
                .build();

        facade.logout(logoutParams);

        assertNull(refreshTokenService.getByToken(refreshToken));
    }

    @Test
    void testAuthentication_Logout_RefreshTokenExpired()
    {
        final var response = Mockito.mock(HttpServletResponse.class);

        final var logoutParams = LogoutParam.builder()
                .withRefreshToken("other-refresh-token")
                .withResponse(response)
                .build();

        assertThrows(RefreshTokenExpiredException.class, () -> facade.logout(logoutParams));
    }

    @Test
    void testAuthentication_Logout_WithoutRequiredRefreshTokenField()
    {
        final var response = Mockito.mock(HttpServletResponse.class);

        final var logoutParams = LogoutParam.builder()
                .withRefreshToken(null)
                .withResponse(response)
                .build();

        assertThrows(BadRequestParameterException.class, () -> facade.logout(logoutParams));
    }

    @Test
    void testAuthentication_Logout_WithoutRequiredResponseField()
    {
        final var logoutParams = LogoutParam.builder()
                .withRefreshToken("other-refresh-token")
                .withResponse(null)
                .build();

        assertThrows(BadRequestParameterException.class, () -> facade.logout(logoutParams));
    }

    private RegistrationParam mockRegisterParams()
    {
        return RegistrationParam.builder()
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
