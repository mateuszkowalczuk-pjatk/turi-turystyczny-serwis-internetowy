package com.turi.image.domain.port;

import com.turi.image.domain.model.Image;

import java.util.List;

public interface ImageService
{
    Image getByAccountId(final Long accountId);

    List<Image> getAllByTouristicPlaceId(final Long touristicPlaceId);

    List<Image> getAllByStayId(final Long stayId);

    List<Image> getAllByAttractionId(final Long attractionId);

    void createForAccount(final Long accountId, final String path);

    void createForTouristicPlace(final Long touristicPlaceId, final String path);

    void createForStay(final Long stayId, final String path);

    void createForAttraction(final Long attractionId, final String path);

    void deleteById(final Long id);

    void deleteAllByStayId(final Long stayId);

    void deleteAllByAttractionId(final Long attractionId);
}
