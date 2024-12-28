package com.turi.image.infrastructure.adapter.service;

import com.turi.image.domain.exception.LocalStorageException;
import com.turi.image.domain.port.LocalStorageService;
import com.turi.infrastructure.properties.ImageStorageProperties;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@AllArgsConstructor
public class LocalStorageServiceImpl implements LocalStorageService
{
    private final ImageStorageProperties properties;

    @Override
    public String upload(final MultipartFile file, final String fileName)
    {
        try
        {
            final var directory = Paths.get(properties.getPath());

            if (!Files.exists(directory))
            {
                Files.createDirectories(directory);
            }

            final var path = directory.resolve(fileName);
            Files.copy(file.getInputStream(), path);

            return fileName;
        }
        catch (final IOException ex)
        {
            throw new LocalStorageException(ex.getMessage());
        }
    }

    @Override
    public void delete(final String path)
    {
        try
        {
            final var file = Paths.get(properties.getPath(), path);
            Files.deleteIfExists(file);
        }
        catch (final IOException ex)
        {
            throw new LocalStorageException(ex.getMessage());
        }
    }
}
