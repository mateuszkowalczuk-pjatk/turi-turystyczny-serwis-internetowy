package com.turi.user.infrastructure.adapter.interfaces;

import com.turi.user.domain.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserRestController
{
    private final UserFacade facade;

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
                                               @RequestParam final String code)
    {
        return facade.resetUserPassword(resetToken, code);
    }

    @PutMapping("/changeUsername")
    public ResponseEntity<User> changeUserUsername(@RequestParam final String username)
    {
        return facade.changeUserUsername(username);
    }

    @PutMapping("/changeEmail")
    public ResponseEntity<User> changeUserEmail(@RequestParam final String email)
    {
        return facade.changeUserEmail(email);
    }

    @PutMapping("/changePassword")
    public ResponseEntity<User> changeUserPassword(@RequestParam final String password)
    {
        return facade.changeUserPassword(password);
    }
}
