package com.turi.account.infrastructure.adapter.interfaces;

import com.turi.account.domain.model.Account;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/account")
public class AccountRestController
{
    private final AccountFacade facade;

    @GetMapping("/isAddressExists")
    public ResponseEntity<Boolean> isAddressExists(@RequestParam final String country,
                                                   @RequestParam final String city,
                                                   @RequestParam final String zipCode,
                                                   @RequestParam final String street,
                                                   @RequestParam final String buildingNumber,
                                                   @RequestParam(required = false) final Integer apartmentNumber)
    {
        return ResponseEntity.ok(facade.isAddressExists(country, city, zipCode, street, buildingNumber, apartmentNumber));
    }

    @GetMapping("/isPhoneNumberExists")
    public ResponseEntity<Boolean> isPhoneNumberExists(@RequestParam final String phoneNumber)
    {
        return ResponseEntity.ok(facade.isPhoneNumberExists(phoneNumber));
    }

    @PutMapping("/updateAccount/{accountId}")
    public ResponseEntity<Account> updateAccount(@PathVariable final String accountId,
                                                 @RequestBody final Account account)
    {
        return ResponseEntity.ok(facade.updateAccount(accountId, account));
    }
}
