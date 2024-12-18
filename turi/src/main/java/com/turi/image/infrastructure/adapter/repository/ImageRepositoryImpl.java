package com.turi.image.infrastructure.adapter.repository;

import com.turi.image.domain.port.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ImageRepositoryImpl implements ImageRepository
{
    private final ImageRepositoryDao repositoryDao;
}
