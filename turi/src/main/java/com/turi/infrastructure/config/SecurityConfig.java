package com.turi.infrastructure.config;

import com.turi.account.domain.model.AccountType;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig
{
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception
    {
        return http
                .securityMatchers(matchers -> matchers.requestMatchers("/api/**"))
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers(
                            "/auth/register",
                            "/auth/login",
                            "/auth/refresh",
                            "/user/sendResetPasswordCode",
                            "/user/resetPassword"
                    ).permitAll();
                    authorize.requestMatchers("/auth/logout").authenticated();
                    authorize.requestMatchers(
                            "/account/activate",
                            "/account/resendActivateCode"
                    ).hasRole(AccountType.INACTIVE.getName());
//                    authorize.requestMatchers("/x/**").hasRole(AccountType.PREMIUM.getName());
                    authorize.requestMatchers("/**").hasAnyRole(AccountType.NORMAL.getName(), AccountType.PREMIUM.getName());
                    authorize.anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(final AuthenticationConfiguration config) throws Exception
    {
        return config.getAuthenticationManager();
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistrationBean(final CorsFilter corsFilter)
    {
        return new FilterRegistrationBean<>(corsFilter);
    }

    @Bean
    public CorsFilter corsFilter()
    {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildCorsConfiguration());
        return new CorsFilter(source);
    }

    private CorsConfiguration buildCorsConfiguration()
    {
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        return config;
    }
}
