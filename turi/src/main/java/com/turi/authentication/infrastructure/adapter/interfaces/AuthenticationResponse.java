package com.turi.authentication.infrastructure.adapter.interfaces;

import com.turi.authentication.domain.model.Authentication;
import com.turi.infrastructure.exception.BadRequestResponseException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthenticationResponse
{
    public static ResponseEntity<?> of(final Authentication authentication)
    {
        if (authentication == null)
        {
            throw new BadRequestResponseException("Authentication response must not be null.");
        }

        if (authentication.getAccessTokenExpiresIn() == null)
        {
            final var activateTokenCookie = prepareCookie("activateToken", authentication.getAccessToken(), authentication.getRefreshTokenExpiresIn());

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, activateTokenCookie.toString())
                    .build();
        }

        final var accessTokenCookie = prepareCookie("accessToken", authentication.getAccessToken(), authentication.getAccessTokenExpiresIn());

        if (authentication.getRefreshToken() == null)
        {
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                    .build();
        }

        final var refreshTokenCookie = prepareCookie("refreshToken", authentication.getRefreshToken(), authentication.getRefreshTokenExpiresIn());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .build();
    }

    private static ResponseCookie prepareCookie(final String name, final String token, final Long expiresIn)
    {
        return ResponseCookie.from(name, token)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(expiresIn)
                .build();
    }

    public static ResponseEntity<?> of(final HttpServletResponse response)
    {
        final var cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }
}
