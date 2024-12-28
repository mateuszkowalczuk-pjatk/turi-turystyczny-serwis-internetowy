package com.turi.image.infrastructure.adapter.interfaces;

import com.turi.image.domain.model.Image;
import com.turi.infrastructure.exception.BadRequestResponseException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ImageResponse
{
    public static ResponseEntity<List<Image>> of(final List<Image> images)
    {
        if (images == null)
        {
            throw new BadRequestResponseException("Images response must not be null.");
        }

        return ResponseEntity.ok(images);
    }

    public static ResponseEntity<Image> of(final Image image)
    {
        if (image == null)
        {
            throw new BadRequestResponseException("Image response must not be null.");
        }

        return ResponseEntity.ok(image);
    }

    public static ResponseEntity<String> of(final String path)
    {
        if (path == null)
        {
            throw new BadRequestResponseException("Uploaded image path response must not be null.");
        }

        return ResponseEntity.ok(path);
    }
}
