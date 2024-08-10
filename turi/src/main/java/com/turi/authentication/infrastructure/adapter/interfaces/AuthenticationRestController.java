package com.turi.authentication.infrastructure.adapter.interfaces;

import com.turi.account.domain.model.Account;
import com.turi.authentication.infrastructure.adapter.application.queries.authentication.AuthenticationParam;
import com.turi.authentication.infrastructure.adapter.application.queries.registration.RegistrationParam;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthenticationRestController
{
    private final AuthenticationFacade facade;

    @PostMapping("/signUp")
    public ResponseEntity<Account> register(@RequestBody final RegistrationParam params)
    {
        return ResponseEntity.ok(facade.register(params));
    }

    @PostMapping("/signIn")
    public ResponseEntity<Object> authenticate(@RequestBody final AuthenticationParam params)
    {
        return ResponseEntity.ok(facade.authenticate(params));
    }
}
