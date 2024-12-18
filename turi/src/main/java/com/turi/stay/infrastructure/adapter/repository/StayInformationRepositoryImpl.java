package com.turi.stay.infrastructure.adapter.repository;

import com.turi.stay.domain.port.StayInformationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class StayInformationRepositoryImpl implements StayInformationRepository
{
    private StayInformationRepositoryDao repositoryDao;
}
