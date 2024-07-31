package com.turi.owner.infrastructure.adapter.interfaces;

import com.turi.owner.domain.model.Owner;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class OwnerRestController
{
    private final OwnerFacade ownerFacade;

    @GetMapping("/getOwners")
    public List<Owner> getOwners()
    {
        return ownerFacade.getOwners();
    }
}
