package com.turi.touristicplace.infrastructure.adapter.service;

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
}
