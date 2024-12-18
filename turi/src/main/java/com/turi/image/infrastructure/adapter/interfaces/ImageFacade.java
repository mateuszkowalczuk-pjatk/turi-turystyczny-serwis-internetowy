package com.turi.image.infrastructure.adapter.interfaces;

import com.turi.image.domain.port.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ImageFacade
{
    private final ImageService service;
}
