package com.turi.testhelper.utils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

public final class ContextHelper
{
    public static void setContextUserId(final Long id)
    {
        final var authentication = new UsernamePasswordAuthenticationToken(
                String.valueOf(id),
                null,
                Collections.emptyList()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
