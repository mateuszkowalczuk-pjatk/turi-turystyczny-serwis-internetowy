package com.turi.owner.domain.port;

import com.turi.owner.domain.model.Owner;

import java.util.List;

public interface OwnerRepository
{
    List<Owner> getOwners();
}
