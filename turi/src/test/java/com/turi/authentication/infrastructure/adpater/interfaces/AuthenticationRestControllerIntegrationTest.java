package com.turi.authentication.infrastructure.adpater.interfaces;

import com.turi.account.domain.model.AccountType;
import com.turi.account.domain.port.AccountService;
import com.turi.authentication.domain.model.LoginParam;
import com.turi.authentication.domain.model.RegisterParam;
import com.turi.authentication.domain.port.JwtService;
import com.turi.authentication.infrastructure.adapter.interfaces.AuthenticationFacade;
import com.turi.infrastructure.rest.ErrorCode;
import com.turi.premium.domain.port.PremiumService;
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
    private PremiumService premiumService;

    @Autowired(required = false)
    private JwtService jwtService;

    @Autowired(required = false)
    private PasswordEncoder passwordEncoder;

    @Test
    void testAuthentication_Register()
    {
        final var params = mockRegisterParams();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/auth/register")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(params), ResponseEntity.class);

        assertNotNull(result);
        assertTrue(result.getStatusCode().is2xxSuccessful());

        final var cookie = result.getHeaders().get("Set-Cookie");

        assertNotNull(cookie);
        assertTrue(cookie.get(0).contains("activateToken="));
        assertTrue(cookie.get(0).contains("Max-Age=900"));
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
    void testAuthentication_Register_WithoutRequiredEmailField()
    {
        final var params = mockRegisterParams();

        params.setEmail(null);

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/auth/register")
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
                .path("/api/auth/register")
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
                .path("/api/auth/register")
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
                .path("/api/auth/register")
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
                .path("/api/auth/register")
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
                .path("/api/auth/register")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(params), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAuthentication_Login_ByUsername()
    {
        final var params = mockRegisterParams();

        facade.register(params);

        accountService.activate(2L, accountService.getById(2L).getActivationCode());

        final var authParams = LoginParam.builder()
                .withLogin(params.getUsername())
                .withPassword(params.getPassword())
                .build();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/auth/login")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(authParams), ResponseEntity.class);

        assertNotNull(result);
        assertTrue(result.getStatusCode().is2xxSuccessful());

        final var cookie = result.getHeaders().get("Set-Cookie");

        assertNotNull(cookie);

        assertTrue(cookie.get(0).contains("accessToken="));
        assertTrue(cookie.get(0).contains("Max-Age=900"));
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
    void testAuthentication_Login_ByUsername_UserNotFound()
    {
        final var params = mockRegisterParams();

        final var authParams = LoginParam.builder()
                .withLogin(params.getUsername())
                .withPassword(params.getPassword())
                .build();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/auth/login")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(authParams), ErrorCode.class);

        assertNotNull(result);
        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAuthentication_Login_ByUsername_WrongPassword()
    {
        final var params = mockRegisterParams();

        facade.register(params);

        final var authParams = LoginParam.builder()
                .withLogin(params.getUsername())
                .withPassword(mockUser().getPassword())
                .build();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/auth/login")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(authParams), ErrorCode.class);

        assertNotNull(result);
        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAuthentication_Login_ByEmail()
    {
        final var params = mockRegisterParams();

        facade.register(params);

        accountService.activate(2L, accountService.getById(2L).getActivationCode());

        final var authParams = LoginParam.builder()
                .withLogin(params.getEmail())
                .withPassword(params.getPassword())
                .build();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/auth/login")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(authParams), ResponseEntity.class);

        assertNotNull(result);
        assertTrue(result.getStatusCode().is2xxSuccessful());

        final var cookie = result.getHeaders().get("Set-Cookie");

        assertNotNull(cookie);

        assertTrue(cookie.get(0).contains("accessToken="));
        assertTrue(cookie.get(0).contains("Max-Age=900"));
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
    void testAuthentication_Login_ByEmail_UserNotFound()
    {
        final var params = mockRegisterParams();

        final var authParams = LoginParam.builder()
                .withLogin(params.getEmail())
                .withPassword(params.getPassword())
                .build();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/auth/login")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(authParams), ErrorCode.class);

        assertNotNull(result);
        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAuthentication_Login_ByEmail_WrongPassword()
    {
        final var params = mockRegisterParams();

        facade.register(params);

        final var authParams = LoginParam.builder()
                .withLogin(params.getEmail())
                .withPassword(mockUser().getPassword())
                .build();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/auth/login")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(authParams), ErrorCode.class);

        assertNotNull(result);
        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAuthentication_Login_WithoutRequiredLoginField()
    {
        final var authParams = LoginParam.builder()
                .withLogin(null)
                .withPassword(mockUser().getPassword())
                .build();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/auth/login")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(authParams), ErrorCode.class);

        assertNotNull(result);
        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAuthentication_Login_WithoutRequiredPasswordField()
    {
        final var authParams = LoginParam.builder()
                .withLogin(mockUser().getUsername())
                .withPassword(null)
                .build();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/auth/login")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(authParams), ErrorCode.class);

        assertNotNull(result);
        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAuthentication_Login_InActiveAccount()
    {
        final var params = mockRegisterParams();

        facade.register(params);

        final var authParams = LoginParam.builder()
                .withLogin(params.getEmail())
                .withPassword(params.getPassword())
                .build();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/auth/login")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(authParams), ErrorCode.class);

        assertNotNull(result);
        assertTrue(result.getStatusCode().is2xxSuccessful());

        final var cookie = result.getHeaders().get("Set-Cookie");

        assertNotNull(cookie);

        assertTrue(cookie.get(0).contains("activateToken="));
        assertTrue(cookie.get(0).contains("Max-Age=900"));
        assertTrue(cookie.get(0).contains("Secure"));
        assertTrue(cookie.get(0).contains("HttpOnly"));
        assertTrue(cookie.get(0).contains("SameSite=Strict"));
    }

    @Test
    void testAuthentication_Login_PremiumAccount()
    {
        final var registerParams = mockRegisterParams();

        facade.register(registerParams);

        accountService.activate(2L, accountService.getById(2L).getActivationCode());

        accountService.updateAccountTypeToPremium(2L);

        final var loginParams = LoginParam.builder()
                .withLogin(registerParams.getEmail())
                .withPassword(registerParams.getPassword())
                .build();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/auth/login")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(loginParams), ErrorCode.class);

        assertNotNull(result);
        assertTrue(result.getStatusCode().is2xxSuccessful());

        final var cookie = result.getHeaders().get("Set-Cookie");

        assertNotNull(cookie);

        assertTrue(cookie.get(0).contains("loginToken="));
        assertTrue(cookie.get(0).contains("Max-Age=900"));
        assertTrue(cookie.get(0).contains("Secure"));
        assertTrue(cookie.get(0).contains("HttpOnly"));
        assertTrue(cookie.get(0).contains("SameSite=Strict"));
    }

    @Test
    void testAuthentication_LoginPremium()
    {
        final var registerParams = mockRegisterParams();

        facade.register(registerParams);

        accountService.activate(2L, accountService.getById(2L).getActivationCode());

        accountService.updateAccountTypeToPremium(2L);

        facade.login(LoginParam.builder()
                .withLogin(registerParams.getEmail())
                .withPassword(registerParams.getPassword())
                .build());

        final var premium = premiumService.getByAccount(2L);

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/auth/loginPremium")
                .queryParam("code", premium.getLoginCode())
                .build().toUri();

        headers.add("Cookie", "loginToken=" + premium.getLoginToken());

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(headers), ErrorCode.class);

        assertNotNull(result);
        assertTrue(result.getStatusCode().is2xxSuccessful());

        final var cookie = result.getHeaders().get("Set-Cookie");

        assertNotNull(cookie);

        assertTrue(cookie.get(1).contains("accessToken="));
        assertTrue(cookie.get(1).contains("Max-Age=900"));
        assertTrue(cookie.get(1).contains("Secure"));
        assertTrue(cookie.get(1).contains("HttpOnly"));
        assertTrue(cookie.get(1).contains("SameSite=Strict"));

        assertTrue(cookie.get(2).contains("refreshToken="));
        assertTrue(cookie.get(2).contains("Max-Age=604800"));
        assertTrue(cookie.get(2).contains("Secure"));
        assertTrue(cookie.get(2).contains("HttpOnly"));
        assertTrue(cookie.get(2).contains("SameSite=Strict"));
    }

    @Test
    void testAuthentication_LoginPremium_WithoutRequiredLoginTokenField()
    {
        final var registerParams = mockRegisterParams();

        facade.register(registerParams);

        accountService.activate(2L, accountService.getById(2L).getActivationCode());

        accountService.updateAccountTypeToPremium(2L);

        facade.login(LoginParam.builder()
                .withLogin(registerParams.getEmail())
                .withPassword(registerParams.getPassword())
                .build());

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/auth/loginPremium")
                .queryParam("code", premiumService.getByAccount(2L).getLoginCode())
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(headers), ErrorCode.class);

        assertNotNull(result);
        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAuthentication_LoginPremium_WithoutRequiredCodeField()
    {
        final var registerParams = mockRegisterParams();

        facade.register(registerParams);

        accountService.activate(2L, accountService.getById(2L).getActivationCode());

        accountService.updateAccountTypeToPremium(2L);

        facade.login(LoginParam.builder()
                .withLogin(registerParams.getEmail())
                .withPassword(registerParams.getPassword())
                .build());

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/auth/loginPremium")
                .build().toUri();

        headers.add("Cookie", "loginToken=" + premiumService.getByAccount(2L).getLoginToken());

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(headers), ErrorCode.class);

        assertNotNull(result);
        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAuthentication_Authorize()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/auth/authorize")
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockUser().getUserId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(headers), ErrorCode.class);

        assertNotNull(result);
        assertTrue(result.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testAuthentication_Authorize_Unauthorized()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/auth/authorize")
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(2L, AccountType.NORMAL.getName()));

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(headers), ErrorCode.class);

        assertNotNull(result);
        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAuthentication_Authorize_ContextIdIsNull()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/auth/authorize")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(headers), ErrorCode.class);

        assertNotNull(result);
        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAuthentication_Refresh()
    {
        final var params = mockRegisterParams();

        facade.register(params);

        accountService.activate(2L, accountService.getById(2L).getActivationCode());

        final var authParams = LoginParam.builder()
                .withLogin(params.getUsername())
                .withPassword(params.getPassword())
                .build();

        final var authentication = facade.login(authParams);

        final var cookie = authentication.getHeaders().get("Set-Cookie");

        assertNotNull(cookie);

        final var refreshToken = cookie.get(1).split("Token=")[1].split(";")[0];

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/auth/refresh")
                .build().toUri();

        headers.add("Cookie", "refreshToken=" + refreshToken);

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(headers), ResponseEntity.class);

        final var resultCookie = result.getHeaders().get("Set-Cookie");

        assertNotNull(resultCookie);

        assertTrue(resultCookie.get(0).contains("accessToken="));
        assertTrue(resultCookie.get(0).contains("Max-Age=900"));
        assertTrue(resultCookie.get(0).contains("Secure"));
        assertTrue(resultCookie.get(0).contains("HttpOnly"));
        assertTrue(resultCookie.get(0).contains("SameSite=Strict"));
    }

    @Test
    void testAuthentication_Refresh_RefreshTokenExpired()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/auth/refresh")
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
                .path("/api/auth/refresh")
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
                .path("/api/auth/refresh")
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

        final var authParams = LoginParam.builder()
                .withLogin(params.getUsername())
                .withPassword(params.getPassword())
                .build();

        final var authentication = facade.login(authParams);

        final var cookie = authentication.getHeaders().get("Set-Cookie");

        assertNotNull(cookie);

        final var refreshToken = cookie.get(1).split("Token=")[1].split(";")[0];

        final var response = Mockito.mock(HttpServletResponse.class);

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/auth/logout")
                .queryParam("response", response)
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockUser().getUserId(), AccountType.NORMAL.getName()));
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
                .path("/api/auth/logout")
                .queryParam("response", response)
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockUser().getUserId(), AccountType.NORMAL.getName()));
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
                .path("/api/auth/logout")
                .queryParam("response", response)
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockUser().getUserId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(headers), ErrorCode.class);

        assertNotNull(result);
        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAuthentication_Logout_WithoutRequiredResponseField()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/auth/logout")
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockUser().getUserId(), AccountType.NORMAL.getName()));
        headers.add("Cookie", "refreshToken=other-refresh-token");

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(headers), ErrorCode.class);

        assertNotNull(result);
        assertTrue(result.getStatusCode().is4xxClientError());
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
                .withUserId(1L)
                .withUsername("Janek")
                .withEmail("jan@turi.com")
                .withPassword("JanKowalski123!")
                .build();
    }
}
