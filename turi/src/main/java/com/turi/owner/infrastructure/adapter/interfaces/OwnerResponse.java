package com.turi.owner.infrastructure.adapter.interfaces;

import com.turi.owner.domain.model.Owner;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OwnerResponse
{
    public static List<Owner> of(final List<Owner> owners)
    {
        Assert.notNull(owners, "Owner list must not be null!");

        return owners;
    }
}
