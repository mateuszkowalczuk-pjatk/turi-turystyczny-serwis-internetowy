package com.turi.authentication.infrastructure.adapter.interfaces;

import com.turi.account.domain.model.Account;
import com.turi.authentication.domain.model.Authentication;
import com.turi.authentication.infrastructure.adapter.application.queries.authentication.AuthenticationParam;
import com.turi.authentication.infrastructure.adapter.application.queries.registration.RegistrationParam;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthenticationRestController
{
    private final AuthenticationFacade facade;

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody final RegistrationParam params)
    {
        return ResponseEntity.ok(facade.register(params));
    }

    @PostMapping("/login")
    public ResponseEntity<Authentication> authenticate(@RequestBody final AuthenticationParam params)
    {
        return ResponseEntity.ok(facade.authenticate(params));
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Account> logout()
    {
        //ToDo - dodanie endpointu do wylogowywania usera, czyli do usuwania tokena z bazy
        return null;
    }
}
