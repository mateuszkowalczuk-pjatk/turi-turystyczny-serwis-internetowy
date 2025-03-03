package com.turi.user.infrastructure.adapter.interfaces;

import com.turi.infrastructure.exception.BadRequestResponseException;
import com.turi.user.domain.model.RefreshToken;
import com.turi.user.domain.model.ResetToken;
import com.turi.user.domain.model.User;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserResponse
{
    public static ResponseEntity<String> of(final String content)
    {
        if (content == null)
        {
            throw new BadRequestResponseException("User content must not be null.");
        }

        return ResponseEntity.ok(content);
    }

    public static ResponseEntity<?> of(final User user)
    {
        if (user == null)
        {
            throw new BadRequestResponseException("User response must not be null.");
        }

        return ResponseEntity.ok().build();
    }

    public static ResponseEntity<Boolean> of(final Boolean result)
    {
        if (result == null)
        {
            throw new BadRequestResponseException("User check result response must not be null.");
        }

        return ResponseEntity.ok(result);
    }

    public static ResponseEntity<?> of(final ResetToken resetToken)
    {
        return prepareResponse("resetToken", resetToken.getToken(), resetToken.getExpiresIn());
    }

    public static ResponseEntity<?> of(final RefreshToken refreshToken, final HttpServletResponse response)
    {
        if (response != null)
        {
            response.addHeader("Set-Cookie", "resetToken=; Max-Age=0; Path=/; HttpOnly; Secure; SameSite=None");
        }

        return prepareResponse("refreshToken", refreshToken.getRefreshToken(), refreshToken.getRefreshTokenExpiresIn());
    }

    private static ResponseEntity<?> prepareResponse(final String name, final String token, final Long expiresIn)
    {
        final var cookie = ResponseCookie.from(name, token)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(expiresIn)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }
}
