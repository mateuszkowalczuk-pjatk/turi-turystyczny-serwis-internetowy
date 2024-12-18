package com.turi.touristicplace.infrastructure.adapter.repository;

import com.turi.touristicplace.domain.port.GuaranteedServiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class GuaranteedServiceRepositoryImpl implements GuaranteedServiceRepository
{
    private final GuaranteedServiceRepositoryDao repositoryDao;
}
