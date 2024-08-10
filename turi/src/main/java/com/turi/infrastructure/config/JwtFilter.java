package com.turi.infrastructure.config;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter
{
    private final SecurityProperties properties;

    @Override
    protected void doFilterInternal(@NotNull final HttpServletRequest request,
                                    @NotNull final HttpServletResponse response,
                                    @NotNull final FilterChain filterChain) throws ServletException, IOException
    {
        final var header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer "))
        {
            final var token = header.substring(7);

            try {
                final var claims = Jwts.parserBuilder()
                        .setSigningKey(properties.getSecretKey().getBytes())
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                final var username = claims.getSubject();

                if (username != null)
                {
                    final var auth = new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            Collections.emptyList()
                    );

                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
            catch (final Exception e)
            {
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }
}