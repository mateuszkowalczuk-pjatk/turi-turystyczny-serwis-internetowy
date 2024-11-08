package com.turi.authentication.infrastructure.adpater.interfaces;

import com.turi.account.domain.model.AccountType;
import com.turi.account.domain.port.AccountService;
import com.turi.authentication.infrastructure.adapter.application.queries.authentication.AuthenticationParam;
import com.turi.authentication.infrastructure.adapter.application.queries.registration.RegistrationParam;
import com.turi.authentication.infrastructure.adapter.interfaces.AuthenticationFacade;
import com.turi.infrastructure.rest.ErrorCode;
import com.turi.testhelper.annotation.RestControllerTest;
import com.turi.testhelper.rest.AbstractRestControllerIntegrationTest;
import com.turi.user.domain.model.User;
import com.turi.user.domain.port.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@RestControllerTest
class AuthenticationRestControllerIntegrationTest extends AbstractRestControllerIntegrationTest
{
    @Autowired(required = false)
    private AuthenticationFacade facade;

    @Autowired(required = false)
    private UserService userService;

    @Autowired(required = false)
    private AccountService accountService;

    @Autowired(required = false)
    private PasswordEncoder passwordEncoder;

    @Test
    void testAuthentication_Register()
    {
        final var params = mockRegisterParams();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/auth/register")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(params), ResponseEntity.class);

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

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/auth/register")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(params), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAuthentication_Register_WithoutRequiredEmailField()
    {
        final var params = mockRegisterParams();

        params.setEmail(null);

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/auth/register")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(params), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAuthentication_Register_WithoutRequiredPasswordField()
    {
        final var params = mockRegisterParams();

        params.setPassword(null);

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/auth/register")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(params), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAuthentication_Register_UniqueUsername()
    {
        final var params = mockRegisterParams();

        params.setUsername(mockUser().getUsername());

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/auth/register")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(params), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAuthentication_Register_UniqueEmail()
    {
        final var params = mockRegisterParams();

        params.setEmail(mockUser().getEmail());

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/auth/register")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(params), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @ParameterizedTest
    @CsvSource({"marek@", "@", "marek", "@marek", "@marek@"})
    void testAuthentication_Register_InvalidEmail(final String email)
    {
        final var params = mockRegisterParams();

        params.setEmail(email);

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/auth/register")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(params), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @ParameterizedTest
    @CsvSource({"Marek123", "marekmarek", "marek123", "Marek1", "marek123!"})
    void testAuthentication_Register_InvalidPassword(final String password)
    {
        final var params = mockRegisterParams();

        params.setPassword(password);

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/auth/register")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(params), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
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

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/auth/login")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(authParams), ResponseEntity.class);

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

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/auth/login")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(authParams), ErrorCode.class);

        assertNotNull(result);
        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAuthentication_Authenticate_ByUsername_WrongPassword()
    {
        final var params = mockRegisterParams();

        facade.register(params);

        final var authParams = AuthenticationParam.builder()
                .withLogin(params.getUsername())
                .withPassword(mockUser().getPassword())
                .build();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/auth/login")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(authParams), ErrorCode.class);

        assertNotNull(result);
        assertTrue(result.getStatusCode().is4xxClientError());
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

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/auth/login")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(authParams), ResponseEntity.class);

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

        final var authParams = AuthenticationParam.builder()
                .withLogin(params.getEmail())
                .withPassword(params.getPassword())
                .build();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/auth/login")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(authParams), ErrorCode.class);

        assertNotNull(result);
        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAuthentication_Authenticate_ByEmail_WrongPassword()
    {
        final var params = mockRegisterParams();

        facade.register(params);

        final var authParams = AuthenticationParam.builder()
                .withLogin(params.getEmail())
                .withPassword(mockUser().getPassword())
                .build();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/auth/login")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(authParams), ErrorCode.class);

        assertNotNull(result);
        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAuthentication_Authenticate_WithoutRequiredLoginField()
    {
        final var authParams = AuthenticationParam.builder()
                .withLogin(null)
                .withPassword(mockUser().getPassword())
                .build();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/auth/login")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(authParams), ErrorCode.class);

        assertNotNull(result);
        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAuthentication_Authenticate_WithoutRequiredPasswordField()
    {
        final var authParams = AuthenticationParam.builder()
                .withLogin(mockUser().getUsername())
                .withPassword(null)
                .build();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/auth/login")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(authParams), ErrorCode.class);

        assertNotNull(result);
        assertTrue(result.getStatusCode().is4xxClientError());
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

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/auth/refresh")
                .build().toUri();

        headers.add("Cookie", "refreshToken=" + refreshToken);

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(headers), ResponseEntity.class);

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
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/auth/refresh")
                .build().toUri();

        headers.add("Cookie", "refreshToken=sample-refresh-token");

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(headers), ErrorCode.class);

        assertNotNull(result);
        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAuthentication_Refresh_RefreshTokenNotFound()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/auth/refresh")
                .build().toUri();

        headers.add("Cookie", "refreshToken=other-refresh-token");

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(headers), ErrorCode.class);

        assertNotNull(result);
        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAuthentication_Refresh_WithoutRequiredRefreshTokenField()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/auth/refresh")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(headers), ErrorCode.class);

        assertNotNull(result);
        assertTrue(result.getStatusCode().is4xxClientError());
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

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/auth/logout")
                .queryParam("response", response)
                .build().toUri();

        headers.add("Cookie", "refreshToken=" + refreshToken);

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(headers), ResponseEntity.class);

        assertNotNull(result);
        assertTrue(result.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testAuthentication_Logout_RefreshTokenExpired()
    {
        final var response = Mockito.mock(HttpServletResponse.class);

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/auth/logout")
                .queryParam("response", response)
                .build().toUri();

        headers.add("Cookie", "refreshToken=sample-refresh-token");

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(headers), ErrorCode.class);

        assertNotNull(result);
        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAuthentication_Logout_WithoutRequiredRefreshTokenField()
    {
        final var response = Mockito.mock(HttpServletResponse.class);

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/auth/logout")
                .queryParam("response", response)
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(headers), ErrorCode.class);

        assertNotNull(result);
        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAuthentication_Logout_WithoutRequiredResponseField()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/auth/logout")
                .build().toUri();

        headers.add("Cookie", "refreshToken=other-refresh-token");

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(headers), ErrorCode.class);

        assertNotNull(result);
        assertTrue(result.getStatusCode().is4xxClientError());
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
