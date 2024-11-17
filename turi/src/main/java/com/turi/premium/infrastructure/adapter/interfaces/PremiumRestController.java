package com.turi.premium.infrastructure.adapter.interfaces;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/premium", produces = "application/json")
public class PremiumRestController
{
    private final PremiumFacade facade;
}
