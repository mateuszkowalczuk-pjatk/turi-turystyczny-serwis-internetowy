package com.turi.image.infrastructure.adapter.interfaces;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/image", produces = "application/json")
public class ImageRestController
{
    private final ImageFacade facade;
}
