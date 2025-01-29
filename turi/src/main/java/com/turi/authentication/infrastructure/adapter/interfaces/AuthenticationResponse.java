package com.turi.authentication.infrastructure.adapter.interfaces;

import com.turi.authentication.domain.exception.UnauthorizedException;
import com.turi.authentication.domain.model.Authentication;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthenticationResponse
{
    public static ResponseEntity<?> of(final Authentication authentication)
    {
        if (authentication.getLoginToken() != null)
        {
            final var loginTokenCookie = prepareCookie("loginToken", authentication.getLoginToken(), authentication.getAccessTokenExpiresIn());

            return ResponseEntity.status(HttpStatus.CREATED)
                    .header(HttpHeaders.SET_COOKIE, loginTokenCookie.toString())
                    .build();
        }

        if (authentication.getAccessTokenExpiresIn() == null)
        {
            final var activateTokenCookie = prepareCookie("activateToken", authentication.getAccessToken(), authentication.getRefreshTokenExpiresIn());

            return ResponseEntity.accepted()
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

    public static ResponseEntity<?> of(final Authentication authentication, final HttpServletResponse response)
    {
        cookieCleaning(response, "loginToken");

        final var accessTokenCookie = prepareCookie("accessToken", authentication.getAccessToken(), authentication.getAccessTokenExpiresIn());

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
                .sameSite("None")
                .path("/")
                .maxAge(expiresIn)
                .build();
    }

    public static ResponseEntity<?> of(final Boolean condition)
    {
        if (!condition)
        {
            throw new UnauthorizedException();
        }

        return ResponseEntity.ok().build();
    }

    public static ResponseEntity<?> of(final HttpServletResponse response)
    {
        cookieCleaning(response, "accessToken");

        cookieCleaning(response, "refreshToken");

        return ResponseEntity.ok().build();
    }

    private static void cookieCleaning(final HttpServletResponse response, final String name)
    {
        if (response != null)
        {
            final var cookie = new Cookie(name, null);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            cookie.setMaxAge(0);

            response.addCookie(cookie);
        }
    }
}
