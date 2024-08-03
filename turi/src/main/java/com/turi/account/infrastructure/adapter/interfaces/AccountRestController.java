package com.turi.account.infrastructure.adapter.interfaces;

import com.turi.account.domain.model.Account;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class AccountRestController
{
    private final AccountFacade facade;

    @GetMapping("/isAccountAddressExists")
    public ResponseEntity<Boolean> isAddressExists(@RequestParam final String country,
                                                   @RequestParam final String city,
                                                   @RequestParam final String zipCode,
                                                   @RequestParam final String street,
                                                   @RequestParam final String buildingNumber,
                                                   @RequestParam(required = false) final Integer apartmentNumber)
    {
        return ResponseEntity.ok(facade.isAddressExists(country, city, zipCode, street, buildingNumber, apartmentNumber));
    }

    @GetMapping("/isAccountLoginExists")
    public ResponseEntity<Boolean> isLoginExists(@RequestParam final String login)
    {
        return ResponseEntity.ok(facade.isLoginExists(login));
    }

    @GetMapping("/isAccountEmailExists")
    public ResponseEntity<Boolean> isEmailExists(@RequestParam final String email)
    {
        return ResponseEntity.ok(facade.isEmailExists(email));
    }

    @GetMapping("/isAccountPhoneNumberExists")
    public ResponseEntity<Boolean> isPhoneNumberExists(@RequestParam final String phoneNumber)
    {
        return ResponseEntity.ok(facade.isPhoneNumberExists(phoneNumber));
    }

    @PutMapping("/updateAccountOwnerDetails/{accountId}")
    public ResponseEntity<Account> updateOwnerDetails(@PathVariable final String accountId,
                                                      @RequestBody final Account account)
    {
        return ResponseEntity.ok(facade.updateOwnerDetails(accountId, account));
    }

    @PutMapping("/resetAccountPassword/{accountId}")
    public ResponseEntity<Account> resetPassword(@PathVariable final String accountId,
                                                 @RequestParam final String password)
    {
        return ResponseEntity.ok(facade.resetPassword(accountId, password));
    }
}
