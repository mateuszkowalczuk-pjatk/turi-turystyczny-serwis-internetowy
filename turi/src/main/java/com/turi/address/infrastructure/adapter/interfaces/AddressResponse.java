package com.turi.address.infrastructure.adapter.interfaces;

import com.turi.address.domain.model.Address;
import com.turi.infrastructure.exception.BadRequestResponseException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AddressResponse
{
    public static ResponseEntity<Address> of(final Address address)
    {
        if (address == null)
        {
            throw new BadRequestResponseException("Address must not be null.");
        }

        return ResponseEntity.ok(address);
    }
}
