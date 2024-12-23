package com.turi.image.domain.port;

import org.springframework.web.multipart.MultipartFile;

public interface LocalStorageService
{
    String upload(final MultipartFile file, final String fileName);

    void delete(final String path);
}
