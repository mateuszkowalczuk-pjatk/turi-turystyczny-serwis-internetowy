package com.turi.address.infrastructure.adapter.interfaces;

import com.turi.address.domain.model.Address;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/address")
public class AddressRestController
{
    private final AddressFacade addressFacade;

    @PostMapping("/createAddress")
    public ResponseEntity<Address> createAddress(@RequestBody final Address address)
    {
        return ResponseEntity.ok(addressFacade.createAddress(address));
    }
}
