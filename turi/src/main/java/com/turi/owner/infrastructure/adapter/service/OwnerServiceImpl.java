package com.turi.owner.infrastructure.adapter.service;

import com.turi.owner.domain.model.Owner;
import com.turi.owner.domain.port.OwnerRepository;
import com.turi.owner.domain.port.OwnerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class OwnerServiceImpl implements OwnerService
{
    private final OwnerRepository ownerRepository;

    @Override
    public List<Owner> getOwners()
    {
        return ownerRepository.getOwners();
    }
}
