package com.turi.attraction.infrastructure.adapter.service;

import com.turi.attraction.domain.port.AttractionRepository;
import com.turi.attraction.domain.port.AttractionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AttractionServiceImpl implements AttractionService
{
    private final AttractionRepository repository;
}
