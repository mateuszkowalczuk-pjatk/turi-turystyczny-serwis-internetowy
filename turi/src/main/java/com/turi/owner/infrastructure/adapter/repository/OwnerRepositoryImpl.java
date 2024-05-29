package com.turi.owner.infrastructure.adapter.repository;

import com.turi.owner.domain.model.Owner;
import com.turi.owner.domain.port.OwnerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class OwnerRepositoryImpl implements OwnerRepository
{
    private final OwnerRepositoryDao ownerRepositoryDao;

    @Override
    public List<Owner> getOwners()
    {
        return ownerRepositoryDao.findAll().stream()
                .map(Owner::of)
                .collect(Collectors.toList());
    }
}
