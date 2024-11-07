package com.turi.authentication.infrastructure.adpater.service;

import com.turi.account.domain.model.AccountType;
import com.turi.authentication.domain.port.JwtService;
import com.turi.infrastructure.config.SecurityProperties;
import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.testhelper.annotation.ServiceTest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ServiceTest
class JwtServiceTest
{
    @Autowired
    private JwtService service;

    @Autowired
    private SecurityProperties properties;

    @Test
    void testAuthentication_GenerateToken()
    {
        final var subject = 1L;

        final var role = AccountType.NORMAL.getName();

        final var result = service.generateToken(subject, role);

        assertNotNull(result);

        final var claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(properties.getSecretKey().getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(result)
                .getBody();

        assertEquals(subject, Long.parseLong(claims.getSubject()));
        assertEquals(role, claims.get("role", String.class));
        assertThat(claims.getExpiration().getTime()).isGreaterThan(System.currentTimeMillis());
    }

    @Test
    void testAuthentication_GenerateToken_SubjectIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> service.generateToken(null, AccountType.NORMAL.getName()));
    }

    @Test
    void testAuthentication_GenerateToken_RoleIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> service.generateToken(1L, null));
    }
}
