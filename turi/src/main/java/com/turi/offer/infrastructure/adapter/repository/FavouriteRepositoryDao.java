package com.turi.offer.infrastructure.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavouriteRepositoryDao extends JpaRepository<FavouriteEntity, FavouriteId>
{
    @Query(value = """
    SELECT * FROM favourite f
    WHERE f.accountId = :accountId
    """, nativeQuery = true)
    List<FavouriteEntity> findAllByAccountId(@Param("accountId") final Long accountId);

    @Query(value = """
    SELECT * FROM favourite f
    WHERE f.accountId = :accountId AND f.touristicPlaceId = :touristicPlaceId
    """, nativeQuery = true)
    Optional<FavouriteEntity> findByAccountIdAndTouristicPlaceId(@Param("accountId") final Long accountId,
                                                                 @Param("touristicPlaceId") final Long touristicPlaceId);

    @Query(value = """
    DELETE FROM favourite f
    WHERE f.accountId = :accountId AND f.touristicplaceid = :touristicPlaceId
    """, nativeQuery = true)
    void deleteByAccountIdAndTouristicPlaceId(@Param("accountId") final Long accountId,
                                              @Param("touristicPlaceId") final Long touristicPlaceId);
}
