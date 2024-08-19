package com.turi.user.infrastructure.adapter.interfaces;

import com.turi.infrastructure.rest.ErrorCode;
import com.turi.testhelper.annotation.RestControllerTest;
import com.turi.testhelper.rest.AbstractRestControllerIntegrationTest;
import com.turi.user.domain.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.net.URI;

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

    @Test
    void testUser_IsUsernameExists()
    {
        final var user = mockUser();

        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/isUserUsernameExists")
                .queryParam("username", user.getUsername())
                .build().toUri();

        final var result = restTemplate.getForEntity(uri, Boolean.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals(Boolean.TRUE, result.getBody());
    }

    @Test
    void testUser_IsUsernameExists_NotExists()
    {
        final var user = mockNewUser();

        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/isUserUsernameExists")
                .queryParam("username", user.getUsername())
                .build().toUri();

        final var result = restTemplate.getForEntity(uri, Boolean.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals(Boolean.FALSE, result.getBody());
    }

    @Test
    void testUser_IsEmailExists()
    {
        final var user = mockUser();

        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/isUserEmailExists")
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

        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/isUserEmailExists")
                .queryParam("email", user.getEmail())
                .build().toUri();

        final var result = restTemplate.getForEntity(uri, Boolean.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals(Boolean.FALSE, result.getBody());
    }

    @Test
    void testUser_ChangeUsername()
    {
        final var user = mockUser();

        user.setUsername(mockNewUser().getUsername());

        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/changeUserUsername/{userId}")
                .queryParam("username", user.getUsername())
                .buildAndExpand(user.getUserId())
                .toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(null), User.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertNotNull(result.getBody());
        assertThat(result.getBody().getUserId()).isEqualTo(user.getUserId());
        assertThat(result.getBody().getUsername()).isEqualTo(user.getUsername());
        assertThat(result.getBody().getEmail()).isEqualTo(user.getEmail());
        assertThat(result.getBody().getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    void testUser_ChangeUsername_UniqueUsername()
    {
        final var user = facade.createUser(mockNewUser());

        user.setUsername(mockUser().getUsername());

        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/changeUserUsername/{userId}")
                .queryParam("username", user.getUsername())
                .buildAndExpand(user.getUserId())
                .toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(null), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testUser_ChangeUsername_UserNotFound()
    {
        final var user = mockNewUser();

        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/changeUserUsername/{userId}")
                .queryParam("username", user.getUsername())
                .buildAndExpand(user.getUserId())
                .toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(null), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testUser_ChangeEmail()
    {
        final var user = mockUser();

        user.setEmail(mockNewUser().getEmail());

        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/changeUserEmail/{userId}")
                .queryParam("email", user.getEmail())
                .buildAndExpand(user.getUserId())
                .toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(null), User.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertNotNull(result.getBody());
        assertThat(result.getBody().getUserId()).isEqualTo(user.getUserId());
        assertThat(result.getBody().getUsername()).isEqualTo(user.getUsername());
        assertThat(result.getBody().getEmail()).isEqualTo(user.getEmail());
        assertThat(result.getBody().getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    void testUser_ChangeEmail_UniqueEmail()
    {
        final var user = facade.createUser(mockNewUser());

        user.setEmail(mockUser().getEmail());

        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/changeUserEmail/{userId}")
                .queryParam("email", user.getEmail())
                .buildAndExpand(user.getUserId())
                .toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(null), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testUser_ChangeEmail_UserNotFound()
    {
        final var user = mockNewUser();

        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/changeUserEmail/{userId}")
                .queryParam("email", user.getEmail())
                .buildAndExpand(user.getUserId())
                .toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(null), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testUser_ChangePassword()
    {
        final var user = mockUser();

        user.setPassword(mockNewUser().getPassword());

        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/changeUserPassword/{userId}")
                .queryParam("password", user.getPassword())
                .buildAndExpand(user.getUserId())
                .toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(null), User.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertNotNull(result.getBody());
        assertThat(result.getBody().getUserId()).isEqualTo(user.getUserId());
        assertThat(result.getBody().getUsername()).isEqualTo(user.getUsername());
        assertThat(result.getBody().getEmail()).isEqualTo(user.getEmail());
        assertTrue(passwordEncoder.matches(user.getPassword(), result.getBody().getPassword()));
    }

    @ParameterizedTest
    @CsvSource({"Marek123", "marekmarek", "marek123", "Marek1", "marek123!"})
    void testUser_ChangePassword_InvalidPassword(final String password)
    {
        final var user = mockUser();

        user.setPassword(password);

        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/changeUserPassword/{userId}")
                .queryParam("password", user.getPassword())
                .buildAndExpand(user.getUserId())
                .toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(null), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testUser_ChangePassword_UserNotFound()
    {
        final var user = mockNewUser();

        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/changeUserPassword/{userId}")
                .queryParam("password", user.getPassword())
                .buildAndExpand(user.getUserId())
                .toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(null), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
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
