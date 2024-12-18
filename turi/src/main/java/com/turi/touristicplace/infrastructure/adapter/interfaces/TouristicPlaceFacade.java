package com.turi.touristicplace.infrastructure.adapter.interfaces;

import com.turi.touristicplace.domain.port.TouristicPlaceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TouristicPlaceFacade
{
    private final TouristicPlaceService service;
}
