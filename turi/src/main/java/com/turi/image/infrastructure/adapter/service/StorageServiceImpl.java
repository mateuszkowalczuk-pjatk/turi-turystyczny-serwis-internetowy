package com.turi.image.infrastructure.adapter.service;

import com.turi.image.domain.exception.ImageStorageModeException;
import com.turi.image.domain.port.AzureBlobStorageService;
import com.turi.image.domain.port.LocalStorageService;
import com.turi.image.domain.port.StorageService;
import com.turi.infrastructure.properties.ImageStorageProperties;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class StorageServiceImpl implements StorageService
{
    private final ImageStorageProperties properties;
    private final LocalStorageService localStorageService;
    private final AzureBlobStorageService azureBlobStorageService;

    @Override
    public String upload(final MultipartFile file, final String fileName)
    {
        return switch (properties.getMode())
        {
            case "azure" -> azureBlobStorageService.upload(file, fileName);
            case "local" -> localStorageService.upload(file, fileName);
            default -> throw new ImageStorageModeException(properties.getMode());
        };
    }

    @Override
    public void delete(final String path)
    {
        switch (properties.getMode())
        {
            case "azure" -> azureBlobStorageService.delete(path);
            case "local" -> localStorageService.delete(path);
            default -> throw new ImageStorageModeException(properties.getMode());
        }
    }
}
