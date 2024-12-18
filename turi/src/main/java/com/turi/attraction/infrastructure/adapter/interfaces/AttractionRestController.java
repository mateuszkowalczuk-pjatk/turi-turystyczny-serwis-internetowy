package com.turi.attraction.infrastructure.adapter.interfaces;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/attraction", produces = "application/json")
public class AttractionRestController
{
    private final AttractionFacade facade;
}
