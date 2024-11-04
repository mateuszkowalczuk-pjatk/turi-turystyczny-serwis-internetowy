package com.turi.infrastructure.common;

import com.turi.infrastructure.exception.BadRequestParameterException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class ContextHandler
{
    public static Long getIdFromContext()
    {
        final var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null)
        {
            throw new BadRequestParameterException("Invalid request for account.");
        }

        return Long.parseLong((String) authentication.getPrincipal());
    }
}
