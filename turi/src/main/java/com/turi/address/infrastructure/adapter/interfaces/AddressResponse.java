package com.turi.address.infrastructure.adapter.interfaces;

import com.turi.address.domain.model.Address;
import io.jsonwebtoken.lang.Assert;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AddressResponse
{
    public static Address of(final Address address)
    {
        Assert.notNull(address, "Address must not be null.");

        return address;
    }
}
