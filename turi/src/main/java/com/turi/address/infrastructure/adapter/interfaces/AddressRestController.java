package com.turi.address.infrastructure.adapter.interfaces;

import com.turi.address.domain.model.Address;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/address")
public class AddressRestController
{
    private final AddressFacade facade;

    @GetMapping("/getById/{addressId}")
    public ResponseEntity<?> getAddressById(@PathVariable final String addressId)
    {
        return facade.getAddressById(addressId);
    }

    @PostMapping("/create")
    public ResponseEntity<Address> createAddress(@RequestBody final Address address)
    {
        return facade.createAddress(address);
    }
}
