package com.turi.owner.infrastructure.adapter.interfaces;

import com.turi.owner.domain.model.Owner;
import com.turi.owner.domain.port.OwnerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class OwnerFacade
{
    private final OwnerService ownerService;

    public List<Owner> getOwners()
    {
        return OwnerResponse.of(ownerService.getOwners());
    }
}
