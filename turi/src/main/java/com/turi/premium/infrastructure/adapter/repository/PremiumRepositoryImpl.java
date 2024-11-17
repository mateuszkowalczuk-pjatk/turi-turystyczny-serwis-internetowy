package com.turi.premium.infrastructure.adapter.repository;

import com.turi.premium.domain.port.PremiumRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class PremiumRepositoryImpl implements PremiumRepository
{
    private final PremiumRepositoryDao repositoryDao;
}
