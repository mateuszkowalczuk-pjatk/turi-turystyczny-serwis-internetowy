package com.turi.user.infrastructure.adapter.interfaces;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/user", produces = "application/json")
public class UserRestController
{
    private final UserFacade facade;

    @GetMapping("/getUsername")
    public ResponseEntity<String> getUserUsername()
    {
        return facade.getUserUsername();
    }

    @GetMapping("/getEmail")
    public ResponseEntity<String> getUserEmail()
    {
        return facade.getUserEmail();
    }

    @GetMapping("/isUsernameExists")
    public ResponseEntity<Boolean> isUserUsernameExists(@RequestParam final String username)
    {
        return facade.isUserUsernameExists(username);
    }

    @GetMapping("/isEmailExists")
    public ResponseEntity<Boolean> isUserEmailExists(@RequestParam final String email)
    {
        return facade.isUserEmailExists(email);
    }

    @PostMapping("/sendResetPasswordCode")
    public ResponseEntity<?> sendResetUserPasswordCode(@RequestParam final String email)
    {
        return facade.sendResetUserPasswordCode(email);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetUserPassword(@CookieValue(value = "resetToken") final String resetToken,
                                               @RequestParam final String code,
                                               final HttpServletResponse response)
    {
        return facade.resetUserPassword(resetToken, code, response);
    }

    @PutMapping("/changeUsername")
    public ResponseEntity<?> changeUserUsername(@RequestParam final String username)
    {
        return facade.changeUserUsername(username);
    }

    @PutMapping("/changeEmail")
    public ResponseEntity<?> changeUserEmail(@RequestParam final String email)
    {
        return facade.changeUserEmail(email);
    }

    @PutMapping("/changePassword")
    public ResponseEntity<?> changeUserPassword(@RequestParam final String password)
    {
        return facade.changeUserPassword(password);
    }
}
