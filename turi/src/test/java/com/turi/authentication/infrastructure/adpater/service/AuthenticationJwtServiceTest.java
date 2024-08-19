package com.turi.authentication.infrastructure.adpater.service;

import com.turi.authentication.domain.port.AuthenticationJwtService;
import com.turi.infrastructure.config.SecurityProperties;
import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.testhelper.annotation.ServiceTest;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ServiceTest
class AuthenticationJwtServiceTest
{
    @Autowired
    private AuthenticationJwtService service;

    @Autowired
    private SecurityProperties properties;

    @Test
    void testAuthentication_GenerateToken()
    {
        final var subject = "Janek";

        final var result = service.generateToken(subject);

        assertNotNull(result);

        final var claims = Jwts.parserBuilder()
                .setSigningKey(Base64.getDecoder().decode(properties.getSecretKey()))
                .build()
                .parseClaimsJws(result)
                .getBody();

        assertEquals(subject, claims.getSubject());
        assertThat(claims.getExpiration().getTime()).isGreaterThan(System.currentTimeMillis());
    }

    @Test
    void testAuthentication_GenerateToken_SubjectIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> service.generateToken(null));
    }

    @Test
    void testAuthentication_GenerateToken_SubjectIsEmpty()
    {
        assertThrows(BadRequestParameterException.class, () -> service.generateToken(""));
    }
}
