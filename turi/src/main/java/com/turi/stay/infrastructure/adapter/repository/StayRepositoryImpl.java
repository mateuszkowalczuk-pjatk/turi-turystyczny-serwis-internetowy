package com.turi.stay.infrastructure.adapter.repository;

import com.turi.stay.domain.port.StayRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class StayRepositoryImpl implements StayRepository
{
    private final StayRepositoryDao repositoryDao;
}
