package com.turi.authentication.infrastructure.adpater.interfaces;

import com.turi.account.domain.model.AccountType;
import com.turi.authentication.domain.exception.InvalidLoginException;
import com.turi.authentication.domain.exception.InvalidPasswordForLoginException;
import com.turi.authentication.infrastructure.adapter.application.queries.authentication.AuthenticationParam;
import com.turi.authentication.infrastructure.adapter.application.queries.registration.RegistrationParam;
import com.turi.authentication.infrastructure.adapter.interfaces.AuthenticationFacade;
import com.turi.infrastructure.config.SecurityProperties;
import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.testhelper.annotation.RestControllerTest;
import com.turi.user.domain.exception.UserUniqueEmailException;
import com.turi.user.domain.exception.UserUniqueUsernameException;
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

@RestControllerTest
class AuthenticationFacadeTest
{
    @Autowired(required = false)
    private AuthenticationFacade facade;

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

        final var result = facade.register(params);

        assertNotNull(result);
        assertNotNull(result.getUserId());
        assertThat(result.getAccountType()).isEqualTo(AccountType.NORMAL);

        final var user = userService.getById(result.getUserId());

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

        assertThrows(BadRequestParameterException.class, () -> facade.register(params));
    }

    @Test
    void testAuthentication_Register_WithoutRequiredEmailField()
    {
        final var params = mockRegistration();

        params.setEmail(null);

        assertThrows(BadRequestParameterException.class, () -> facade.register(params));
    }

    @Test
    void testAuthentication_Register_WithoutRequiredPasswordField()
    {
        final var params = mockRegistration();

        params.setPassword(null);

        assertThrows(BadRequestParameterException.class, () -> facade.register(params));
    }

    @Test
    void testAuthentication_Register_UniqueUsername()
    {
        final var params = mockRegistration();

        params.setUsername(mockUser().getUsername());

        assertThrows(UserUniqueUsernameException.class, () -> facade.register(params));
    }

    @Test
    void testAuthentication_Register_UniqueEmail()
    {
        final var params = mockRegistration();

        params.setEmail(mockUser().getEmail());

        assertThrows(UserUniqueEmailException.class, () -> facade.register(params));
    }

    @ParameterizedTest
    @CsvSource({"Marek123", "marekmarek", "marek123", "Marek1", "marek123!"})
    void testAuthentication_Register_InvalidPassword(final String password)
    {
        final var params = mockRegistration();

        params.setPassword(password);

        assertThrows(BadRequestParameterException.class, () -> facade.register(params));
    }

    @Test
    void testAuthentication_Authenticate_ByUsername()
    {
        final var params = mockRegistration();

        facade.register(params);

        final var authenticationParam = AuthenticationParam.builder()
                .withLogin(params.getUsername())
                .withPassword(params.getPassword())
                .build();

        final var result = facade.authenticate(authenticationParam);

        assertNotNull(result);

        final var claims = Jwts.parserBuilder()
                .setSigningKey(Base64.getDecoder().decode(properties.getSecretKey()))
                .build()
                .parseClaimsJws(result.getToken())
                .getBody();

        assertEquals(authenticationParam.getLogin(), claims.getSubject());
        assertThat(result.getExpiresIn()).isEqualTo(properties.getExpirationTime());
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

        assertThrows(InvalidLoginException.class, () -> facade.authenticate(authenticationParams));
    }

    @Test
    void testAuthentication_Authenticate_ByUsername_WrongPassword()
    {
        final var params = mockRegistration();

        facade.register(params);

        final var authenticationParams = AuthenticationParam.builder()
                .withLogin(params.getUsername())
                .withPassword(mockUser().getPassword())
                .build();

        assertThrows(InvalidPasswordForLoginException.class, () -> facade.authenticate(authenticationParams));
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

        assertThrows(BadRequestParameterException.class, () -> facade.authenticate(authenticationParams));
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

        assertThrows(BadRequestParameterException.class, () -> facade.authenticate(authenticationParams));
    }

    @Test
    void testAuthentication_Authenticate_ByEmail()
    {
        final var params = mockRegistration();

        facade.register(params);

        final var authenticationParams = AuthenticationParam.builder()
                .withLogin(params.getEmail())
                .withPassword(params.getPassword())
                .build();

        final var result = facade.authenticate(authenticationParams);

        assertNotNull(result);

        final var claims = Jwts.parserBuilder()
                .setSigningKey(Base64.getDecoder().decode(properties.getSecretKey()))
                .build()
                .parseClaimsJws(result.getToken())
                .getBody();

        assertEquals(authenticationParams.getLogin(), claims.getSubject());
        assertThat(result.getExpiresIn()).isEqualTo(properties.getExpirationTime());
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

        assertThrows(InvalidLoginException.class, () -> facade.authenticate(authenticationParams));
    }

    @Test
    void testAuthentication_Authenticate_ByEmail_WrongPassword()
    {
        final var params = mockRegistration();

        facade.register(params);

        final var authenticationParams = AuthenticationParam.builder()
                .withLogin(params.getEmail())
                .withPassword(mockUser().getPassword())
                .build();

        assertThrows(InvalidPasswordForLoginException.class, () -> facade.authenticate(authenticationParams));
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

        assertThrows(BadRequestParameterException.class, () -> facade.authenticate(authenticationParams));
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

        assertThrows(BadRequestParameterException.class, () -> facade.authenticate(authenticationParams));
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
