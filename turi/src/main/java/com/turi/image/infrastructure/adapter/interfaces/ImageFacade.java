package com.turi.image.infrastructure.adapter.interfaces;

import com.turi.image.domain.model.Image;
import com.turi.image.domain.model.ImageMode;
import com.turi.image.domain.port.ImageService;
import com.turi.infrastructure.common.ContextHandler;
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

    public ResponseEntity<Image> getImageByAccount()
    {
        final var accountId = ContextHandler.getIdFromContext();

        return ImageResponse.of(service.getByAccountId(accountId));
    }

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
        if (file == null || mode == null)
        {
            throw new BadRequestParameterException("Parameters file and mode must not be null.");
        }

        imageValidation(file);

        if (mode.equals(ImageMode.ACCOUNT))
        {
            final var accountId = ContextHandler.getIdFromContext();

            return ImageResponse.of(service.upload(file, mode, accountId));
        }

        if (id == null)
        {
            throw new BadRequestParameterException("Parameter id must not be null.");
        }

        return ImageResponse.of(service.upload(file, mode, ObjectId.of(id).getValue()));
    }

    private void imageValidation(final MultipartFile file)
    {
        if (file.getSize() > 2 * 1024 * 1024)
        {
            throw new BadRequestParameterException("Uploaded image must not exceed 2 MB in size.");
        }

        if (!"image/png".equals(file.getContentType())
                && !"image/jpg".equals(file.getContentType())
                && !"image/jpeg".equals(file.getContentType()))
        {
            throw new BadRequestParameterException("Uploaded image must be in format PNG or JPG.");
        }
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
