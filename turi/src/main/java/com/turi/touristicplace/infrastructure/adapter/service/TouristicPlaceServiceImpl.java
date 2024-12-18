package com.turi.touristicplace.infrastructure.adapter.service;

import com.turi.touristicplace.domain.port.TouristicPlaceRepository;
import com.turi.touristicplace.domain.port.TouristicPlaceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TouristicPlaceServiceImpl implements TouristicPlaceService
{
    private final TouristicPlaceRepository repository;
}
