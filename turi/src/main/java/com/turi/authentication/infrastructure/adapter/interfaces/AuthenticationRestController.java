package com.turi.authentication.infrastructure.adapter.interfaces;

import com.turi.authentication.infrastructure.adapter.application.queries.authentication.AuthenticationParam;
import com.turi.authentication.infrastructure.adapter.application.queries.logout.LogoutParam;
import com.turi.authentication.infrastructure.adapter.application.queries.refresh.RefreshParam;
import com.turi.authentication.infrastructure.adapter.application.queries.registration.RegistrationParam;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/auth", produces = "application/json")
public class AuthenticationRestController
{
    private final AuthenticationFacade facade;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody final RegistrationParam params)
    {
        return facade.register(params);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody final AuthenticationParam params)
    {
        return facade.authenticate(params);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@CookieValue(value = "refreshToken") final String refreshToken)
    {
        final var params = RefreshParam.builder()
                .withRefreshToken(refreshToken)
                .build();

        return facade.refresh(params);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue(value = "refreshToken") final String refreshToken, final HttpServletResponse response)
    {
        final var params = LogoutParam.builder()
                .withRefreshToken(refreshToken)
                .withResponse(response)
                .build();

        return facade.logout(params);
    }
}
