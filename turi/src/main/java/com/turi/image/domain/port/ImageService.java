package com.turi.image.domain.port;

import com.turi.image.domain.model.Image;
import com.turi.image.domain.model.ImageMode;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService
{
    Image getByAccountId(final Long accountId);

    List<Image> getAllByTouristicPlaceId(final Long touristicPlaceId);

    List<Image> getAllByStayId(final Long stayId);

    List<Image> getAllByAttractionId(final Long attractionId);

    String upload(final MultipartFile file, final ImageMode mode, final Long id);

    void deleteById(final Long id);

    void deleteAllByStayId(final Long stayId);

    void deleteAllByAttractionId(final Long attractionId);
}
