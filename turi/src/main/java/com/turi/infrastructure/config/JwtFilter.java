package com.turi.infrastructure.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
        final String path = request.getRequestURI();

        if (path.startsWith("/api/auth/register")
                || path.startsWith("/api/auth/login")
                || path.startsWith("/api/auth/refresh")
                || path.startsWith("/api/user/sendResetPasswordCode")
                || path.startsWith("/api/user/resetPassword")
                || path.startsWith("/api/user/isUsernameExists")
                || path.startsWith("/api/user/isEmailExists")
                || path.startsWith("/api/premium/login")
                || path.startsWith("/api/payment/webhook")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs"))
        {
            filterChain.doFilter(request, response);
            return;
        }

        String token = null;

        final var header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer "))
        {
            token = header.substring(7);
        }

        if (token == null && (path.startsWith("/api/account/activate")))
        {
            final var cookies = request.getCookies();
            if (cookies != null)
            {
                for (final var cookie : cookies)
                {
                    if ("activateToken".equals(cookie.getName()))
                    {
                        token = cookie.getValue();
                        break;
                    }
                }
            }

            if (token == null)
            {
                SecurityContextHolder.clearContext();

                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Authorization token is required to activate account.");

                return;
            }
        }

        if (token == null)
        {
            final var cookies = request.getCookies();
            if (cookies != null)
            {
                for (final var cookie : cookies)
                {
                    if ("accessToken".equals(cookie.getName()))
                    {
                        token = cookie.getValue();
                        break;
                    }
                    else if ("activateToken".equals(cookie.getName()))
                    {
                        token = cookie.getValue();
                        break;
                    }
                }
            }
        }

        if (token != null)
        {
            try
            {
                final var key = Keys.hmacShaKeyFor(properties.getSecretKey().getBytes(StandardCharsets.UTF_8));

                final var claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                final var id = claims.getSubject();

                final var role = claims.get("role", String.class);

                if (id != null && role != null)
                {
                    final var authentication = new UsernamePasswordAuthenticationToken(
                            id,
                            null,
                            Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role))
                    );

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            catch (final Exception ex)
            {
                SecurityContextHolder.clearContext();

                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());

                return;
            }
        }
        else
        {
            SecurityContextHolder.clearContext();

            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization token is required to access this resource.");

            return;
        }

        filterChain.doFilter(request, response);
    }
}