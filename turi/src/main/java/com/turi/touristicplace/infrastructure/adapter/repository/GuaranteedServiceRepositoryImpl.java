package com.turi.touristicplace.infrastructure.adapter.repository;

import com.turi.touristicplace.domain.model.GuaranteedService;
import com.turi.touristicplace.domain.port.GuaranteedServiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class GuaranteedServiceRepositoryImpl implements GuaranteedServiceRepository
{
    private final GuaranteedServiceRepositoryDao repositoryDao;

    @Override
    public List<GuaranteedService> findAllByTouristicPlaceId(final Long touristicPlaceId)
    {
        return repositoryDao.findAllByTouristicPlaceId(touristicPlaceId).stream()
                .map(GuaranteedService::of)
                .toList();
    }
}
