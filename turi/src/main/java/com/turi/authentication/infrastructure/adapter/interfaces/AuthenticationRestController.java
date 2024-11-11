package com.turi.authentication.infrastructure.adapter.interfaces;

import com.turi.authentication.domain.model.LoginParam;
import com.turi.authentication.domain.model.LogoutParam;
import com.turi.authentication.domain.model.RefreshParam;
import com.turi.authentication.domain.model.RegisterParam;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/auth", produces = "application/json")
public class AuthenticationRestController
{
    private final AuthenticationFacade facade;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody final RegisterParam params)
    {
        return facade.register(params);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody final LoginParam params)
    {
        return facade.login(params);
    }

    @PostMapping("/authorize")
    public ResponseEntity<?> authorize()
    {
        return facade.authorize();
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@CookieValue(value = "refreshToken", required = false) final String refreshToken)
    {
        final var params = RefreshParam.builder()
                .withRefreshToken(refreshToken)
                .build();

        return facade.refresh(params);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue(value = "refreshToken", required = false) final String refreshToken, final HttpServletResponse response)
    {
        final var params = LogoutParam.builder()
                .withRefreshToken(refreshToken)
                .withResponse(response)
                .build();

        return facade.logout(params);
    }
}
