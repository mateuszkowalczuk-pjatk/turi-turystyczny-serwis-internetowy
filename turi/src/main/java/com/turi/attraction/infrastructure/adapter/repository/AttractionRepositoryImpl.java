package com.turi.attraction.infrastructure.adapter.repository;

import com.turi.attraction.domain.port.AttractionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class AttractionRepositoryImpl implements AttractionRepository
{
    private final AttractionRepositoryDao repositoryDao;
}
