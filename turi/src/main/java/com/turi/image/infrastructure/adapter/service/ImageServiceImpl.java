package com.turi.image.infrastructure.adapter.service;

import com.turi.image.domain.port.ImageRepository;
import com.turi.image.domain.port.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService
{
    private final ImageRepository repository;
}
