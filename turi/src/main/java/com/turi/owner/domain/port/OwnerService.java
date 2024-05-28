package com.turi.owner.domain.port;

import com.turi.owner.domain.model.Owner;

import java.util.List;

public interface OwnerService
{
    List<Owner> getOwners();
}
