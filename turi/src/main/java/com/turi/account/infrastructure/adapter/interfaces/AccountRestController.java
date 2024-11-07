package com.turi.account.infrastructure.adapter.interfaces;

import com.turi.account.domain.model.Account;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/account", produces = "application/json")
public class AccountRestController
{
    private final AccountFacade facade;

    @GetMapping("/getById")
    public ResponseEntity<Account> getAccountById()
    {
        return facade.getAccountById();
    }

    @GetMapping("/isAddressExists")
    public ResponseEntity<Boolean> isAccountAddressExists(@RequestParam final String country,
                                                          @RequestParam final String city,
                                                          @RequestParam final String zipCode,
                                                          @RequestParam final String street,
                                                          @RequestParam final String buildingNumber,
                                                          @RequestParam(required = false) final String apartmentNumber)
    {
        return facade.isAccountAddressExists(country, city, zipCode, street, buildingNumber, apartmentNumber);
    }

    @GetMapping("/isPhoneNumberExists")
    public ResponseEntity<Boolean> isAccountPhoneNumberExists(@RequestParam final String phoneNumber)
    {
        return facade.isAccountPhoneNumberExists(phoneNumber);
    }

    @PostMapping("/activate")
    public ResponseEntity<?> activateAccount(@RequestParam final String code)
    {
        return facade.activateAccount(code);
    }

    @PostMapping("/resendActivateCode")
    public ResponseEntity<?> resendAccountActivationCode()
    {
        return facade.sendAccountActivateCode();
    }

    @PutMapping("/update")
    public ResponseEntity<Account> updateAccount(@RequestBody final Account account)
    {
        return facade.updateAccount(account);
    }
}
