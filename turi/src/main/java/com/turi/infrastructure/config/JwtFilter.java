package com.turi.infrastructure.config;

import com.turi.infrastructure.exception.UnauthorizedException;
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
//        final var path = request.getRequestURI();
//
//        if (path.equals("/") || path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs"))
//        {
//            filterChain.doFilter(request, response);
//
//            return;
//        }

        final var header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer "))
        {
            final var token = header.substring(7);

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
                            Collections.singletonList(new SimpleGrantedAuthority(role))
                    );

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            catch (final Exception ex)
            {
                SecurityContextHolder.clearContext();

                throw new UnauthorizedException(ex.getMessage());
            }
        }
//        else
//        {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.getWriter().write("Unauthorized: No Authorization header provided");
//
//            return;
//        }

        filterChain.doFilter(request, response);
    }
}