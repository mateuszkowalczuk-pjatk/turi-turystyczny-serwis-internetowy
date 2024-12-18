package com.turi.attraction.infrastructure.adapter.interfaces;

import com.turi.attraction.domain.port.AttractionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AttractionFacade
{
    private final AttractionService service;
}
