package com.turi.authentication.infrastructure.adapter.service;

import com.turi.authentication.domain.port.AuthenticationJwtService;
import com.turi.infrastructure.config.SecurityProperties;
import com.turi.infrastructure.exception.BadRequestParameterException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

@Getter
@Service
@AllArgsConstructor
public class AuthenticationJwtServiceImpl implements AuthenticationJwtService
{
    private final SecurityProperties properties;

    @Override
    public String generateToken(final String subject)
    {
        if (Objects.isNull(subject) || subject.isBlank())
        {
            throw new BadRequestParameterException("Subject must not be null or empty.");
        }

        final var currentTime = System.currentTimeMillis();

        final var key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(properties.getSecretKey()));

        return Jwts.builder()
                .setClaims(new HashMap<String, String>())
                .setSubject(subject)
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(currentTime + properties.getExpirationTime()))
                .signWith(key)
                .compact();
    }
}
