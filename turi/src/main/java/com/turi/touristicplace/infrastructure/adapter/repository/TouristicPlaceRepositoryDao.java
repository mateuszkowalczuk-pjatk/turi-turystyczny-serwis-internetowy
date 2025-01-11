package com.turi.touristicplace.infrastructure.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TouristicPlaceRepositoryDao extends JpaRepository<TouristicPlaceEntity, Long>
{
    @Query(value = """
        SELECT DISTINCT ON (tp.touristicplaceid) tp.*, COALESCE(ts_rank(to_tsvector('simple', tp.name), plainto_tsquery('simple', :query)), 0) AS rank
        FROM touristicplace tp
        JOIN stay s ON tp.touristicplaceid = s.touristicplaceid
        WHERE (to_tsvector('simple', tp.name) @@ plainto_tsquery('simple', :query) OR tp.name ILIKE '%' || :query || '%')
        AND (:dateTo IS NULL OR s.dateFrom <= CAST(:dateTo AS DATE))
        AND (:dateFrom IS NULL OR s.dateTo >= CAST(:dateFrom AS DATE))
        AND (:touristicPlaceId IS NULL OR :rank IS NULL OR (COALESCE(ts_rank(to_tsvector('simple', tp.name), plainto_tsquery('simple', :query)), 0) <= :rank AND tp.touristicplaceid > :touristicPlaceId))
        ORDER BY tp.touristicplaceid, rank DESC NULLS LAST
        LIMIT :limit
    """, nativeQuery = true)
    List<Object[]> findForSearch(@Param("query") final String query,
                                 @Param("dateFrom") final String dateFrom,
                                 @Param("dateTo") final String dateTo,
                                 @Param("limit") final Long limit,
                                 @Param("touristicPlaceId") final Long touristicPlaceId,
                                 @Param("rank") final Double rank);

    @Query(value = """
        SELECT DISTINCT ON (tp.touristicplaceid) tp.*, COALESCE(ts_rank(to_tsvector('simple', s.name), plainto_tsquery('simple', :query)), 0) AS rank
        FROM touristicplace tp
        JOIN stay s ON tp.touristicplaceid = s.touristicplaceid
        WHERE (:query IS NULL OR (to_tsvector('simple', s.name) @@ plainto_tsquery('simple', :query) OR s.name ILIKE '%' || :query || '%'))
        AND (:dateTo IS NULL OR s.dateFrom <= CAST(:dateTo AS DATE))
        AND (:dateFrom IS NULL OR s.dateTo >= CAST(:dateFrom AS DATE))
        AND (:touristicPlaceType IS NULL OR tp.touristicplacetype = :touristicPlaceType)
        AND (:touristicPlaceId IS NULL OR :rank IS NULL OR (COALESCE(ts_rank(to_tsvector('simple', s.name), plainto_tsquery('simple', :query)), 0) <= :rank AND tp.touristicplaceid > :touristicPlaceId))
        ORDER BY tp.touristicplaceid, rank DESC NULLS LAST
        LIMIT :limit
    """, nativeQuery = true)
    List<Object[]> findByStaysForSearch(@Param("query") final String query,
                                        @Param("dateFrom") final String dateFrom,
                                        @Param("dateTo") final String dateTo,
                                        @Param("touristicPlaceType") final Integer touristicPlaceType,
                                        @Param("limit") final Long limit,
                                        @Param("touristicPlaceId") final Long touristicPlaceId,
                                        @Param("rank") final Double rank);


    @Query(value = """
        SELECT DISTINCT ON (tp.touristicplaceid) tp.*, COALESCE(ts_rank(to_tsvector('simple', a.name), plainto_tsquery('simple', :query)), 0) AS rank
        FROM touristicplace tp
        JOIN attraction a ON tp.touristicplaceid = a.touristicplaceid
        WHERE (:query IS NULL OR (to_tsvector('simple', a.name) @@ plainto_tsquery('simple', :query) OR a.name ILIKE '%' || :query || '%'))
        AND (:dateTo IS NULL OR a.dateFrom <= CAST(:dateTo AS DATE))
        AND (:dateFrom IS NULL OR a.dateTo >= CAST(:dateFrom AS DATE))
        AND (:attractionType IS NULL OR a.attractiontype = :attractionType)
        AND (:touristicPlaceId IS NULL OR :rank IS NULL OR (COALESCE(ts_rank(to_tsvector('simple', a.name), plainto_tsquery('simple', :query)), 0) <= :rank AND tp.touristicplaceid > :touristicPlaceId))
        ORDER BY tp.touristicplaceid, rank DESC NULLS LAST
        LIMIT :limit
    """, nativeQuery = true)
    List<Object[]> findByAttractionsForSearch(@Param("query") final String query,
                                              @Param("dateFrom") final String dateFrom,
                                              @Param("dateTo") final String dateTo,
                                              @Param("attractionType") final Integer attractionType,
                                              @Param("limit") final Long limit,
                                              @Param("touristicPlaceId") final Long touristicPlaceId,
                                              @Param("rank") final Double rank);

    @Query(value = """
    SELECT name FROM (
        SELECT DISTINCT tp.name, ts_rank(to_tsvector('simple', tp.name), plainto_tsquery('simple', :query)) AS rank
        FROM touristicplace tp
        WHERE to_tsvector('simple', tp.name) @@ plainto_tsquery('simple', :query) OR tp.name ILIKE '%' || :query || '%'
        ORDER BY rank DESC NULLS LAST
        LIMIT 5
    ) AS subquery;
    """, nativeQuery = true)
    List<String> findForAutocomplete(@Param("query") final String query);

    Optional<TouristicPlaceEntity> findByPremiumId(final Long premiumId);

    Optional<TouristicPlaceEntity> findByAddressId(final Long addressId);
}
