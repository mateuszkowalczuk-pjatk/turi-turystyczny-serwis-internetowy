package com.turi.user.infrastructure.adapter.interfaces;

import com.turi.account.domain.model.AccountType;
import com.turi.authentication.domain.port.JwtService;
import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.infrastructure.rest.ErrorCode;
import com.turi.testhelper.annotation.RestControllerTest;
import com.turi.testhelper.rest.AbstractRestControllerIntegrationTest;
import com.turi.user.domain.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.net.URI;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@RestControllerTest
class UserRestControllerTest extends AbstractRestControllerIntegrationTest
{
    @Autowired(required = false)
    private UserFacade facade;

    @Autowired(required = false)
    private PasswordEncoder passwordEncoder;

    @Autowired(required = false)
    private JwtService jwtService;

    @Test
    void testUser_IsUserUsernameExists()
    {
        final var user = mockUser();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/user/isUsernameExists")
                .queryParam("username", user.getUsername())
                .build().toUri();

        final var result = restTemplate.getForEntity(uri, Boolean.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals(Boolean.TRUE, result.getBody());
    }

    @Test
    void testUser_IsUserUsernameExists_NotExists()
    {
        final var user = mockNewUser();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/user/isUsernameExists")
                .queryParam("username", user.getUsername())
                .build().toUri();

        final var result = restTemplate.getForEntity(uri, Boolean.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals(Boolean.FALSE, result.getBody());
    }

    @Test
    void testUser_IsUserUsernameExists_UsernameIsNull()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/user/isUsernameExists")
                .build().toUri();

        final var result = restTemplate.getForEntity(uri, BadRequestParameterException.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testUser_IsEmailExists()
    {
        final var user = mockUser();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/user/isEmailExists")
                .queryParam("email", user.getEmail())
                .build().toUri();

        final var result = restTemplate.getForEntity(uri, Boolean.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals(Boolean.TRUE, result.getBody());
    }

    @Test
    void testUser_IsEmailExists_NotExists()
    {
        final var user = mockNewUser();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/user/isEmailExists")
                .queryParam("email", user.getEmail())
                .build().toUri();

        final var result = restTemplate.getForEntity(uri, Boolean.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals(Boolean.FALSE, result.getBody());
    }

    @Test
    void testUser_IsUserEmailExists_EmailIsNull()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/user/IsUserEmailExists")
                .build().toUri();

        final var result = restTemplate.getForEntity(uri, BadRequestParameterException.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testUser_SendResetUserPasswordCode()
    {
        final var user = mockUser();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/user/sendResetPasswordCode")
                .queryParam("email", user.getEmail())
                .build().toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(new HttpHeaders()), User.class);

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
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/user/sendResetPasswordCode")
                .build().toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(new HttpHeaders()), User.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testUser_SendResetUserPasswordCode_UserNotFound()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/user/sendResetPasswordCode")
                .queryParam("email", mockNewUser().getEmail())
                .build().toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(new HttpHeaders()), User.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testUser_SendResetUserPasswordCode_ResetCodeRecentlySent()
    {
        final var mock = mockNewUser();
        mock.setPasswordResetExpiresAt(LocalDateTime.now().plusMinutes(15));

        final var user = facade.createUser(mock);

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/user/sendResetPasswordCode")
                .queryParam("email", user.getEmail())
                .build().toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(new HttpHeaders()), User.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testUser_ResetUserPassword()
    {
        final var mock = mockUser();

        facade.sendResetUserPasswordCode(mock.getEmail());

        final var user = facade.getUserById(mock.getUserId());

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/user/resetPassword")
                .queryParam("code", user.getPasswordResetCode())
                .build().toUri();

        final var headers = new HttpHeaders();
        headers.add("Cookie", "resetToken=" + user.getPasswordResetToken());

        final var result = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>("", headers), User.class);

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
        final var mock = mockUser();

        facade.sendResetUserPasswordCode(mock.getEmail());

        final var user = facade.getUserById(mock.getUserId());

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/user/resetPassword")
                .queryParam("code", user.getPasswordResetCode())
                .build().toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>("", new HttpHeaders()), User.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testUser_ResetUserPassword_CodeIsNull()
    {
        final var mock = mockUser();

        facade.sendResetUserPasswordCode(mock.getEmail());

        final var user = facade.getUserById(mock.getUserId());

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/user/resetPassword")
                .build().toUri();

        final var headers = new HttpHeaders();
        headers.add("Cookie", "resetToken=" + user.getPasswordResetToken());

        final var result = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>("", headers), User.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testUser_ResetUserPassword_UserNotFound()
    {
        final var mock = mockUser();

        facade.sendResetUserPasswordCode(mock.getEmail());

        final var user = facade.getUserById(mock.getUserId());

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/user/resetPassword")
                .queryParam("code", user.getPasswordResetCode())
                .build().toUri();

        final var headers = new HttpHeaders();
        headers.add("Cookie", "resetToken=" + mock.getPasswordResetToken());

        final var result = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>("", headers), User.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testUser_ResetUserPassword_InvalidResetCode()
    {
        final var mock = mockUser();

        facade.sendResetUserPasswordCode(mock.getEmail());

        final var user = facade.getUserById(mock.getUserId());

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/user/resetPassword")
                .queryParam("code", mock.getPasswordResetCode())
                .build().toUri();

        final var headers = new HttpHeaders();
        headers.add("Cookie", "resetToken=" + user.getPasswordResetToken());

        final var result = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>("", headers), User.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testUser_ResetUserPassword_ResetCodeExpired()
    {
        final var mock = mockNewUser();
        mock.setPasswordResetExpiresAt(LocalDateTime.now().minusMinutes(1));

        final var user = facade.createUser(mock);

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/user/resetPassword")
                .queryParam("code", user.getPasswordResetCode())
                .build().toUri();

        final var headers = new HttpHeaders();
        headers.add("Cookie", "resetToken=" + user.getPasswordResetToken());

        final var result = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>("", headers), User.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testUser_ChangeUserUsername()
    {
        final var user = mockUser();

        user.setUsername(mockNewUser().getUsername());

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/user/changeUsername")
                .queryParam("username", user.getUsername())
                .build().toUri();

        final var headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtService.generateToken(user.getUserId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(headers), User.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertNotNull(result.getBody());
        assertThat(result.getBody().getUserId()).isEqualTo(user.getUserId());
        assertThat(result.getBody().getUsername()).isEqualTo(user.getUsername());
        assertThat(result.getBody().getEmail()).isEqualTo(user.getEmail());
        assertThat(result.getBody().getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    void testUser_ChangeUserUsername_UsernameIsNull()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/user/changeUsername")
                .build().toUri();

        final var headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockUser().getUserId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(headers), User.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testUser_ChangeUserUsername_ContextUserIdIsNull()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/user/changeUsername")
                .queryParam("username", mockUser().getUsername())
                .build().toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(new HttpHeaders()), User.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testUser_ChangeUserUsername_UniqueUsername()
    {
        final var user = facade.createUser(mockNewUser());

        user.setUsername(mockUser().getUsername());

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/user/changeUsername")
                .queryParam("username", user.getUsername())
                .build().toUri();

        final var headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtService.generateToken(user.getUserId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(headers), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testUser_ChangeUserUsername_UserNotFound()
    {
        final var user = mockNewUser();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/user/changeUsername")
                .queryParam("username", user.getUsername())
                .build().toUri();

        final var headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtService.generateToken(user.getUserId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(headers), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testUser_ChangeUserEmail()
    {
        final var user = mockUser();

        user.setEmail(mockNewUser().getEmail());

        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/user/changeEmail")
                .queryParam("email", user.getEmail())
                .build().toUri();

        final var headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtService.generateToken(user.getUserId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(headers), User.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertNotNull(result.getBody());
        assertThat(result.getBody().getUserId()).isEqualTo(user.getUserId());
        assertThat(result.getBody().getUsername()).isEqualTo(user.getUsername());
        assertThat(result.getBody().getEmail()).isEqualTo(user.getEmail());
        assertThat(result.getBody().getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    void testUser_ChangeUserEmail_EmailIsNull()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/user/changeEmail")
                .build().toUri();

        final var headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockUser().getUserId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(headers), User.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testUser_ChangeUserEmail_ContextUserIdIsNull()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/user/changeEmail")
                .queryParam("email", mockUser().getEmail())
                .build().toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(new HttpHeaders()), User.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @ParameterizedTest
    @CsvSource({"marek@", "@", "marek", "@marek", "@marek@"})
    void testUser_ChangeUserEmail_InvalidEmail(final String email)
    {
        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/user/changeEmail")
                .queryParam("email", email)
                .build().toUri();

        final var headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockUser().getUserId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(headers), User.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testUser_ChangeUserEmail_UniqueEmail()
    {
        final var user = facade.createUser(mockNewUser());

        user.setEmail(mockUser().getEmail());

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/user/changeEmail")
                .queryParam("email", user.getEmail())
                .build().toUri();

        final var headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtService.generateToken(user.getUserId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(headers), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testUser_ChangeUserEmail_UserNotFound()
    {
        final var user = mockNewUser();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/user/changeEmail")
                .queryParam("email", user.getEmail())
                .build().toUri();

        final var headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtService.generateToken(user.getUserId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(headers), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testUser_ChangeUserPassword()
    {
        final var user = mockUser();

        user.setPassword(mockNewUser().getPassword());

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/user/changePassword")
                .queryParam("password", user.getPassword())
                .build().toUri();

        final var headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtService.generateToken(user.getUserId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(headers), User.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertNotNull(result.getBody());
        assertThat(result.getBody().getUserId()).isEqualTo(user.getUserId());
        assertThat(result.getBody().getUsername()).isEqualTo(user.getUsername());
        assertThat(result.getBody().getEmail()).isEqualTo(user.getEmail());
        assertTrue(passwordEncoder.matches(user.getPassword(), result.getBody().getPassword()));
    }

    @Test
    void testUser_ChangeUserPassword_PasswordIsNull()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/user/changePassword")
                .build().toUri();

        final var headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockUser().getUserId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(headers), User.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testUser_ChangeUserPassword_ContextUserIdIsNull()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/user/changePassword")
                .queryParam("password", mockUser().getPassword())
                .build().toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(new HttpHeaders()), User.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @ParameterizedTest
    @CsvSource({"Marek123", "marekmarek", "marek123", "Marek1", "marek123!"})
    void testUser_ChangeUserPassword_InvalidPassword(final String password)
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/user/changePassword")
                .queryParam("password", password)
                .build().toUri();

        final var headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockUser().getUserId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(headers), User.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testUser_ChangeUserPassword_UserNotFound()
    {
        final var user = mockNewUser();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/user/changePassword")
                .queryParam("password", user.getPassword())
                .build().toUri();

        final var headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtService.generateToken(user.getUserId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(headers), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
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
