package com.turi.touristicplace.infrastructure.adapter.repository;

import com.turi.touristicplace.domain.exception.GuaranteedServiceNotFoundException;
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
    public GuaranteedService findById(final Long id)
    {
        return repositoryDao.findById(id)
                .map(GuaranteedService::of)
                .orElseThrow(() -> new GuaranteedServiceNotFoundException(id));
    }

    @Override
    public List<GuaranteedService> findAllByTouristicPlaceId(final Long touristicPlaceId)
    {
        return repositoryDao.findAllByTouristicPlaceId(touristicPlaceId).stream()
                .map(GuaranteedService::of)
                .toList();
    }

    @Override
    public GuaranteedService findByTouristicPlaceIdAndService(final Long touristicPlaceId, final String service)
    {
        return repositoryDao.findByTouristicPlaceIdAndService(touristicPlaceId, service)
                .map(GuaranteedService::of)
                .orElse(null);
    }

    @Override
    public void insert(final GuaranteedService guaranteedService)
    {
        final var entity = GuaranteedServiceEntity.of(guaranteedService);

        repositoryDao.saveAndFlush(entity);
    }

    @Override
    public void deleteById(final Long id)
    {
        final var guaranteedService = findById(id);

        repositoryDao.deleteById(guaranteedService.getGuaranteedServiceId());
    }
}
