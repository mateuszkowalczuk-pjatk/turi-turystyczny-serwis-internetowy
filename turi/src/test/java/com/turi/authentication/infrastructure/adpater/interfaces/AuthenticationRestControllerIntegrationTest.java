package com.turi.authentication.infrastructure.adpater.interfaces;

import com.turi.account.domain.model.Account;
import com.turi.account.domain.model.AccountType;
import com.turi.authentication.domain.model.Authentication;
import com.turi.authentication.infrastructure.adapter.application.queries.authentication.AuthenticationParam;
import com.turi.authentication.infrastructure.adapter.application.queries.registration.RegistrationParam;
import com.turi.infrastructure.config.SecurityProperties;
import com.turi.infrastructure.rest.ErrorCode;
import com.turi.testhelper.annotation.RestControllerTest;
import com.turi.testhelper.rest.AbstractRestControllerIntegrationTest;
import com.turi.user.domain.model.User;
import com.turi.user.domain.port.UserService;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@RestControllerTest
class AuthenticationRestControllerIntegrationTest extends AbstractRestControllerIntegrationTest
{
    @Autowired(required = false)
    private UserService userService;

    @Autowired(required = false)
    private SecurityProperties properties;

    @Autowired(required = false)
    private PasswordEncoder passwordEncoder;

    @Test
    void testAuthentication_Register()
    {
        final var params = mockRegistration();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/signUp")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, params, Account.class);

        assertNotNull(result);
        assertNotNull(result.getBody());
        assertNotNull(result.getBody().getUserId());
        assertThat(result.getBody().getAccountType()).isEqualTo(AccountType.NORMAL);

        final var user = userService.getById(result.getBody().getUserId());

        assertNotNull(user);
        assertThat(user.getUsername()).isEqualTo(params.getUsername());
        assertThat(user.getEmail()).isEqualTo(params.getEmail());
        assertTrue(passwordEncoder.matches(params.getPassword(), user.getPassword()));
    }

    @Test
    void testAuthentication_Register_WithoutRequiredUsernameField()
    {
        final var params = mockRegistration();

        params.setUsername(null);

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/signUp")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, params, Account.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAuthentication_Register_WithoutRequiredEmailField()
    {
        final var params = mockRegistration();

        params.setEmail(null);

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/signUp")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, params, Account.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAuthentication_Register_WithoutRequiredPasswordField()
    {
        final var params = mockRegistration();

        params.setPassword(null);

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/signUp")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, params, Account.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAuthentication_Register_UniqueUsername()
    {
        final var params = mockRegistration();

        params.setUsername(mockUser().getUsername());

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/signUp")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, params, Account.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAuthentication_Register_UniqueEmail()
    {
        final var params = mockRegistration();

        params.setEmail(mockUser().getEmail());

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/signUp")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, params, Account.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @ParameterizedTest
    @CsvSource({"Marek123", "marekmarek", "marek123", "Marek1", "marek123!"})
    void testAuthentication_Register_InvalidPassword(final String password)
    {
        final var params = mockRegistration();

        params.setPassword(password);

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/signUp")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, params, Account.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAuthentication_Authenticate_ByUsername()
    {
        final var params = mockRegistration();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/signUp")
                .build().toUri();

        restTemplate.postForEntity(uri, params, Account.class);

        final var authenticationParams = AuthenticationParam.builder()
                .withLogin(params.getUsername())
                .withPassword(params.getPassword())
                .build();

        final var authenticateUri = fromHttpUrl(getBaseUrl())
                .path("/signIn")
                .build().toUri();

        final var result = restTemplate.postForEntity(authenticateUri, authenticationParams, Authentication.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertNotNull(result.getBody());

        final var claims = Jwts.parserBuilder()
                .setSigningKey(Base64.getDecoder().decode(properties.getSecretKey()))
                .build()
                .parseClaimsJws(result.getBody().getToken())
                .getBody();

        assertEquals(authenticationParams.getLogin(), claims.getSubject());
        assertThat(result.getBody().getExpiresIn()).isEqualTo(properties.getExpirationTime());
        assertThat(claims.getExpiration().getTime()).isGreaterThan(System.currentTimeMillis());
    }

    @Test
    void testAuthentication_Authenticate_ByUsername_UserNotFound()
    {
        final var params = mockRegistration();

        final var authenticationParams = AuthenticationParam.builder()
                .withLogin(params.getUsername())
                .withPassword(params.getPassword())
                .build();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/signIn")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, authenticationParams, ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAuthentication_Authenticate_ByUsername_WrongPassword()
    {
        final var params = mockRegistration();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/signUp")
                .build().toUri();

        restTemplate.postForEntity(uri, params, Account.class);

        final var authenticationParams = AuthenticationParam.builder()
                .withLogin(params.getUsername())
                .withPassword(mockUser().getPassword())
                .build();

        final var authenticateUri = fromHttpUrl(getBaseUrl())
                .path("/signIn")
                .build().toUri();

        final var result = restTemplate.postForEntity(authenticateUri, authenticationParams, ErrorCode.class);

        System.out.println(result);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAuthentication_Authentication_ByUsername_WithoutRequiredLoginField()
    {
        final var params = mockRegistration();

        params.setUsername(null);

        final var authenticationParams = AuthenticationParam.builder()
                .withLogin(params.getUsername())
                .withPassword(params.getPassword())
                .build();

        final var authenticateUri = fromHttpUrl(getBaseUrl())
                .path("/signIn")
                .build().toUri();

        final var result = restTemplate.postForEntity(authenticateUri, authenticationParams, Authentication.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAuthentication_Authentication_ByUsername_WithoutRequiredPasswordField()
    {
        final var params = mockRegistration();

        params.setPassword(null);

        final var authenticationParams = AuthenticationParam.builder()
                .withLogin(params.getUsername())
                .withPassword(params.getPassword())
                .build();

        final var authenticateUri = fromHttpUrl(getBaseUrl())
                .path("/signIn")
                .build().toUri();

        final var result = restTemplate.postForEntity(authenticateUri, authenticationParams, Authentication.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAuthentication_Authenticate_ByEmail()
    {
        final var params = mockRegistration();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/signUp")
                .build().toUri();

        restTemplate.postForEntity(uri, params, Account.class);

        final var authenticationParams = AuthenticationParam.builder()
                .withLogin(params.getEmail())
                .withPassword(params.getPassword())
                .build();

        final var authenticateUri = fromHttpUrl(getBaseUrl())
                .path("/signIn")
                .build().toUri();

        final var result = restTemplate.postForEntity(authenticateUri, authenticationParams, Authentication.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertNotNull(result.getBody());

        final var claims = Jwts.parserBuilder()
                .setSigningKey(Base64.getDecoder().decode(properties.getSecretKey()))
                .build()
                .parseClaimsJws(result.getBody().getToken())
                .getBody();

        assertEquals(authenticationParams.getLogin(), claims.getSubject());
        assertThat(result.getBody().getExpiresIn()).isEqualTo(properties.getExpirationTime());
        assertThat(claims.getExpiration().getTime()).isGreaterThan(System.currentTimeMillis());
    }

    @Test
    void testAuthentication_Authenticate_ByEmail_UserNotFound()
    {
        final var params = mockRegistration();

        final var authenticationParams = AuthenticationParam.builder()
                .withLogin(params.getEmail())
                .withPassword(params.getPassword())
                .build();

        final var authenticateUri = fromHttpUrl(getBaseUrl())
                .path("/signIn")
                .build().toUri();

        final var result = restTemplate.postForEntity(authenticateUri, authenticationParams, ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAuthentication_Authenticate_ByEmail_WrongPassword()
    {
        final var params = mockRegistration();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/signUp")
                .build().toUri();

        restTemplate.postForEntity(uri, params, Account.class);

        final var authenticationParams = AuthenticationParam.builder()
                .withLogin(params.getEmail())
                .withPassword(mockUser().getPassword())
                .build();

        final var authenticateUri = fromHttpUrl(getBaseUrl())
                .path("/signIn")
                .build().toUri();

        final var result = restTemplate.postForEntity(authenticateUri, authenticationParams, ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAuthentication_Authentication_ByEmail_WithoutRequiredLoginField()
    {
        final var params = mockRegistration();

        params.setEmail(null);

        final var authenticationParams = AuthenticationParam.builder()
                .withLogin(params.getEmail())
                .withPassword(params.getPassword())
                .build();

        final var authenticateUri = fromHttpUrl(getBaseUrl())
                .path("/signIn")
                .build().toUri();

        final var result = restTemplate.postForEntity(authenticateUri, authenticationParams, Authentication.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAuthentication_Authentication_ByEmail_WithoutRequiredPasswordField()
    {
        final var params = mockRegistration();

        params.setPassword(null);

        final var authenticationParams = AuthenticationParam.builder()
                .withLogin(params.getEmail())
                .withPassword(params.getPassword())
                .build();

        final var authenticateUri = fromHttpUrl(getBaseUrl())
                .path("/signIn")
                .build().toUri();

        final var result = restTemplate.postForEntity(authenticateUri, authenticationParams, Authentication.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    private User mockUser()
    {
        return User.builder()
                .withUsername("Janek")
                .withEmail("jan@gmail.com")
                .withPassword("JanKowalski123!")
                .build();
    }

    private RegistrationParam mockRegistration()
    {
        return RegistrationParam.builder()
                .withUsername("Marek")
                .withEmail("marek@gmail.com")
                .withPassword("MarekNowak123!")
                .build();
    }
}
