package com.turi.stay.infrastructure.adapter.interfaces;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/stay", produces = "application/json")
public class StayRestController
{
    private final StayFacade facade;
}
