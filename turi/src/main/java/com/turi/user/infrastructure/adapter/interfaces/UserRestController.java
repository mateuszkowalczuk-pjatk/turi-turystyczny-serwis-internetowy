package com.turi.user.infrastructure.adapter.interfaces;

import com.turi.user.domain.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class UserRestController
{
    private final UserFacade facade;

    @GetMapping("/isUserUsernameExists")
    public ResponseEntity<Boolean> isUsernameExists(@RequestParam final String username)
    {
        return ResponseEntity.ok(facade.isUsernameExists(username));
    }

    @GetMapping("/isUserEmailExists")
    public ResponseEntity<Boolean> isEmailExists(@RequestParam final String email)
    {
        return ResponseEntity.ok(facade.isEmailExists(email));
    }

    @PutMapping("/changeUserUsername/{userId}")
    public ResponseEntity<User> changeUsername(@PathVariable final Long userId,
                                               @RequestParam final String username)
    {
        return ResponseEntity.ok(facade.changeUsername(userId, username));
    }

    @PutMapping("/changeUserEmail/{userId}")
    public ResponseEntity<User> changeEmail(@PathVariable final Long userId,
                                            @RequestParam final String email)
    {
        return ResponseEntity.ok(facade.changeEmail(userId, email));
    }

    @PutMapping("/changeUserPassword/{userId}")
    public ResponseEntity<User> changePassword(@PathVariable final Long userId,
                                               @RequestParam final String password)
    {
        return ResponseEntity.ok(facade.changePassword(userId, password));
    }
}
