package com.turi.stay.infrastructure.adapter.service;

import com.turi.stay.domain.port.StayRepository;
import com.turi.stay.domain.port.StayService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StayServiceImpl implements StayService
{
    private final StayRepository repository;
}
