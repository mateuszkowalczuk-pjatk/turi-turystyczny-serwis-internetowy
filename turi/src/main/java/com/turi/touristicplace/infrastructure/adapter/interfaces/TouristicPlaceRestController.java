package com.turi.touristicplace.infrastructure.adapter.interfaces;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/touristicplace", produces = "application/json")
public class TouristicPlaceRestController
{
    private final TouristicPlaceFacade facade;
}
