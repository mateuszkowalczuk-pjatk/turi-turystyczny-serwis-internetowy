package com.turi.touristicplace.infrastructure.adapter.service;

import com.turi.touristicplace.domain.exception.GuaranteedServiceUniqueException;
import com.turi.touristicplace.domain.model.GuaranteedService;
import com.turi.touristicplace.domain.port.GuaranteedServiceRepository;
import com.turi.touristicplace.domain.port.GuaranteedServiceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GuaranteedServiceServiceImpl implements GuaranteedServiceService
{
    private final GuaranteedServiceRepository repository;

    @Override
    public List<GuaranteedService> getAllByTouristicPlaceId(final Long touristicPlaceId)
    {
        return repository.findAllByTouristicPlaceId(touristicPlaceId);
    }

    @Override
    public void create(final GuaranteedService guaranteedService)
    {
        if (repository.findByTouristicPlaceIdAndService(guaranteedService.getTouristicPlaceId(), guaranteedService.getService()) != null)
        {
            throw new GuaranteedServiceUniqueException(guaranteedService.getTouristicPlaceId(), guaranteedService.getService());
        }

        repository.insert(guaranteedService);
    }

    @Override
    public void deleteById(final Long id)
    {
        repository.deleteById(id);
    }
}
