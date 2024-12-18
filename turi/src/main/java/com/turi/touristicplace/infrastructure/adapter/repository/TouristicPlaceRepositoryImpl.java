package com.turi.touristicplace.infrastructure.adapter.repository;

import com.turi.touristicplace.domain.port.TouristicPlaceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class TouristicPlaceRepositoryImpl implements TouristicPlaceRepository
{
    private final TouristicPlaceRepositoryDao repositoryDao;
}
