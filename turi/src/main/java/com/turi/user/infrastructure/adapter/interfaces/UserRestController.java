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
    public ResponseEntity<Boolean> isUsernameExists(@RequestParam final String username)
    {
        return ResponseEntity.ok(facade.isUsernameExists(username));
    }

    @GetMapping("/isEmailExists")
    public ResponseEntity<Boolean> isEmailExists(@RequestParam final String email)
    {
        return ResponseEntity.ok(facade.isEmailExists(email));
    }

    @PutMapping("/changeUsername/{userId}")
    public ResponseEntity<User> changeUsername(@PathVariable final Long userId,
                                               @RequestParam final String username)
    {
        return ResponseEntity.ok(facade.changeUsername(userId, username));
    }

    @PutMapping("/changeEmail/{userId}")
    public ResponseEntity<User> changeEmail(@PathVariable final Long userId,
                                            @RequestParam final String email)
    {
        return ResponseEntity.ok(facade.changeEmail(userId, email));
    }

    @PutMapping("/changePassword/{userId}")
    public ResponseEntity<User> changePassword(@PathVariable final Long userId,
                                               @RequestParam final String password)
    {
        return ResponseEntity.ok(facade.changePassword(userId, password));
    }
}
