package com.turi.stay.infrastructure.adapter.interfaces;

import com.turi.stay.domain.port.StayService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class StayFacade
{
    private final StayService service;
}
