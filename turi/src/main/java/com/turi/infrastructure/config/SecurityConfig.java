package com.turi.infrastructure.config;

import com.turi.account.domain.model.AccountType;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
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
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(
                                "/api/auth/register",
                                "/api/auth/login",
                                "/api/auth/loginPremium",
                                "/api/auth/refresh",
                                "/api/user/sendResetPasswordCode",
                                "/api/user/resetPassword",
                                "/api/user/isUsernameExists",
                                "/api/user/isEmailExists",
                                "/api/payment/webhook",
                                "/api/offer/search",
                                "/api/offer/autocomplete"
                        ).permitAll()
                        .requestMatchers("/api/account/activate").hasRole(AccountType.INACTIVE.getName())
                        .requestMatchers(
                                "/api/premium/premium/**",
                                "/api/touristicplace/**",
                                "/api/stay/**",
                                "/api/attraction/**"
                        ).hasRole(AccountType.PREMIUM.getName())
                        .requestMatchers("/api/**", "/uploads/**").hasAnyRole(AccountType.NORMAL.getName(), AccountType.PREMIUM.getName())
                        .anyRequest().authenticated())
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
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
        final var config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        final var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }

    @Bean
    public RestTemplate restTemplate()
    {
        return new RestTemplate();
    }
}
