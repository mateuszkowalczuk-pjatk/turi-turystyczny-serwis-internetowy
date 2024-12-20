package com.turi.image.domain.port;

import com.turi.image.domain.model.Image;

import java.util.List;

public interface ImageRepository
{
    Image findById(final Long id);

    Image findByAccountId(final Long accountId);

    List<Image> findAllByTouristicPlaceId(final Long touristicPlaceId);

    List<Image> findAllByStayId(final Long stayId);

    List<Image> findAllByAttractionId(final Long attractionId);

    void insert(final Image image);

    void deleteById(final Long id);

    void deleteAllByStayId(final Long stayId);

    void deleteAllByAttractionId(final Long attractionId);
}
