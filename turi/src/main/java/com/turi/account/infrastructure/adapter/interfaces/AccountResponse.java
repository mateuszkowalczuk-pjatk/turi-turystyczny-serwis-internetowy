package com.turi.account.infrastructure.adapter.interfaces;

import com.turi.account.domain.model.Account;
import com.turi.infrastructure.exception.BadRequestResponseException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AccountResponse
{
    public static ResponseEntity<Account> of(final Account account)
    {
        if (account == null)
        {
            throw new BadRequestResponseException("Account response must not be null.");
        }

        return ResponseEntity.ok(account);
    }

    public static ResponseEntity<Boolean> of(final Boolean result)
    {
        if (result == null)
        {
            throw new BadRequestResponseException("Account check result response must not be null.");
        }

        return ResponseEntity.ok(result);
    }

    public static ResponseEntity<?> of(final HttpServletResponse response)
    {
        if (response != null)
        {
//            final var activateToken = new Cookie("activateToken", null);
//            activateToken.setHttpOnly(true);
//            activateToken.setSecure(true);
//            activateToken.setPath("/");
//            activateToken.setMaxAge(0);
//
//            response.addCookie(activateToken);

            response.addHeader("Set-Cookie", "activateToken=; Max-Age=0; Path=/; HttpOnly; Secure; SameSite=None");
        }

        return ResponseEntity.ok().build();
    }
}
