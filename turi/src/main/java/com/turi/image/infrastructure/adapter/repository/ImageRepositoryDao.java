package com.turi.image.infrastructure.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepositoryDao extends JpaRepository<ImageEntity, Long>
{
    Optional<ImageEntity> findByAccountId(final Long accountId);

    List<ImageEntity> findAllByTouristicPlaceId(final Long touristicPlaceId);

    List<ImageEntity> findAllByStayId(final Long stayId);

    List<ImageEntity> findAllByAttractionId(final Long AttractionId);

    void deleteAllByStayId(final Long stayId);

    void deleteAllByAttractionId(final Long attractionId);
}
