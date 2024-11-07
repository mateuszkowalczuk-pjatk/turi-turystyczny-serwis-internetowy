package com.turi.authentication.infrastructure.adapter.service;

import com.turi.authentication.domain.port.JwtService;
import com.turi.infrastructure.config.SecurityProperties;
import com.turi.infrastructure.exception.BadRequestParameterException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Getter
@Service
@AllArgsConstructor
public class JwtServiceImpl implements JwtService
{
    private final SecurityProperties properties;

    @Override
    public String generateToken(final Long subject, final String role)
    {
        if (subject == null || role == null)
        {
            throw new BadRequestParameterException("Subject and role must not be null.");
        }

        final var currentTime = System.currentTimeMillis();

        final var key = Keys.hmacShaKeyFor(properties.getSecretKey().getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(String.valueOf(subject))
                .claim("role", role)
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(currentTime + properties.getAccessTokenExpirationTime()))
                .signWith(key)
                .compact();
    }
}
