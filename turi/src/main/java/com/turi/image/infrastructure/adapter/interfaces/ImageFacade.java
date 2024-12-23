package com.turi.image.infrastructure.adapter.interfaces;

import com.turi.image.domain.model.Image;
import com.turi.image.domain.model.ImageMode;
import com.turi.image.domain.port.ImageService;
import com.turi.infrastructure.common.ObjectId;
import com.turi.infrastructure.exception.BadRequestParameterException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@AllArgsConstructor
public class ImageFacade
{
    private final ImageService service;

    public ResponseEntity<Image> getImageByAccountId(final String accountId)
    {
        if (accountId == null)
        {
            throw new BadRequestParameterException("Parameter accountId must not be null.");
        }

        final var id = ObjectId.of(accountId).getValue();

        return ImageResponse.of(service.getByAccountId(id));
    }

    public ResponseEntity<List<Image>> getAllImagesByTouristicPlaceId(final String touristicPlaceId)
    {
        if (touristicPlaceId == null)
        {
            throw new BadRequestParameterException("Parameter touristicPlaceId must not be null.");
        }

        final var id = ObjectId.of(touristicPlaceId).getValue();

        return ImageResponse.of(service.getAllByTouristicPlaceId(id));
    }

    public ResponseEntity<List<Image>> getAllImagesByStayId(final String stayId)
    {
        if (stayId == null)
        {
            throw new BadRequestParameterException("Parameter stayId must not be null.");
        }

        final var id = ObjectId.of(stayId).getValue();

        return ImageResponse.of(service.getAllByStayId(id));
    }

    public ResponseEntity<List<Image>> getAllImagesByAttractionId(final String attractionId)
    {
        if (attractionId == null)
        {
            throw new BadRequestParameterException("Parameter attractionId must not be null.");
        }

        final var id = ObjectId.of(attractionId).getValue();

        return ImageResponse.of(service.getAllByAttractionId(id));
    }

    public ResponseEntity<String> uploadImage(final MultipartFile file, final ImageMode mode, final String id)
    {
        if (file == null || mode == null || id == null)
        {
            throw new BadRequestParameterException("Parameters file, mode and id must not be null.");
        }

        return ImageResponse.of(service.upload(file, mode, ObjectId.of(id).getValue()));
    }

    public ResponseEntity<?> deleteImageById(final String id)
    {
        if (id == null)
        {
            throw new BadRequestParameterException("Parameter id must not be null.");
        }

        final var imageId = ObjectId.of(id).getValue();

        service.deleteById(imageId);

        return ResponseEntity.ok().build();
    }

    public void deleteAllImagesByStayId(final Long stayId)
    {
        if (stayId == null)
        {
            throw new BadRequestParameterException("Parameter stayId and path must not be null.");
        }

        service.deleteAllByStayId(stayId);
    }

    public void deleteAllImagesByAttractionId(final Long attractionId)
    {
        if (attractionId == null)
        {
            throw new BadRequestParameterException("Parameter attractionId and path must not be null.");
        }

        service.deleteAllByAttractionId(attractionId);
    }
}
