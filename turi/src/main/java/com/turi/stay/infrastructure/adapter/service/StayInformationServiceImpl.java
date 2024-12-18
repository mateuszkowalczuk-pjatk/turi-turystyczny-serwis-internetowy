package com.turi.stay.infrastructure.adapter.service;

import com.turi.stay.domain.port.StayInformationRepository;
import com.turi.stay.domain.port.StayInformationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StayInformationServiceImpl implements StayInformationService
{
    private final StayInformationRepository repository;
}
