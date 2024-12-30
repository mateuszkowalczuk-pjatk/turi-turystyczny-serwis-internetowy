package com.turi.touristicplace.infrastructure.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TouristicPlaceRepositoryDao extends JpaRepository<TouristicPlaceEntity, Long>
{
//    ToDo - do przerobienia ORDER BY bo nie sortuje po fulltext
    @Query(value = """
        SELECT DISTINCT tp.*
        FROM touristicplace tp
        JOIN stay s ON tp.touristicplaceid = s.touristicplaceid
        WHERE to_tsvector('polish', tp.name) @@ plainto_tsquery('polish', :query)
        AND (:dateFrom IS NULL OR s.dateFrom <= :dateTo)
        AND (:dateTo IS NULL OR s.dateTo >= :dateFrom)
        AND (:cursor IS NULL OR tp.touristicplaceid > :cursor)
        ORDER BY tp.touristicplaceid ASC
        LIMIT :limit;
    """, nativeQuery = true)
    List<TouristicPlaceEntity> findForSearch(@Param("query") final String query,
                                             @Param("dateFrom") final LocalDate dateFrom,
                                             @Param("dateTo") final LocalDate dateTo,
                                             @Param("limit") final Long limit,
                                             @Param("cursor") final Long cursor);

    @Query(value = """
        SELECT DISTINCT tp.*
        FROM touristicplace tp
        JOIN stay s ON tp.touristicplaceid = s.touristicplaceid
        WHERE to_tsvector('polish', s.name) @@ plainto_tsquery('polish', :query)
        AND (:dateFrom IS NULL OR s.dateFrom <= :dateTo)
        AND (:dateTo IS NULL OR s.dateTo >= :dateFrom)
        AND (:touristicplacetype IS NULL OR tp.touristicplacetype = :touristicplacetype)
        AND (:cursor IS NULL OR tp.touristicplaceid > :cursor)
        ORDER BY tp.touristicplaceid ASC
        LIMIT :limit;
    """, nativeQuery = true)
    List<TouristicPlaceEntity> findByStaysForSearch(@Param("query") final String query,
                                                    @Param("dateFrom") final LocalDate dateFrom,
                                                    @Param("dateTo") final LocalDate dateTo,
                                                    @Param("type") final Integer type,
                                                    @Param("limit") final Long limit,
                                                    @Param("cursor") final Long cursor);

    @Query(value = """
        SELECT DISTINCT tp.*
        FROM touristicplace tp
        JOIN attraction a ON tp.touristicplaceid = a.touristicplaceid
        WHERE to_tsvector('polish', a.name) @@ plainto_tsquery('polish', :query)
        AND (:dateFrom IS NULL OR a.dateFrom <= :dateTo)
        AND (:dateTo IS NULL OR a.dateTo >= :dateFrom)
        AND (:attractiontype IS NULL OR a.attractiontype = :attractiontype)
        AND (:cursor IS NULL OR tp.touristicplaceid > :cursor)
        ORDER BY tp.touristicplaceid ASC
        LIMIT :limit;
    """, nativeQuery = true)
    List<TouristicPlaceEntity> findByAttractionsForSearch(@Param("query") final String query,
                                                          @Param("dateFrom") final LocalDate dateFrom,
                                                          @Param("dateTo") final LocalDate dateTo,
                                                          @Param("type") final Integer type,
                                                          @Param("limit") final Long limit,
                                                          @Param("cursor") final Long cursor);

    @Query(value = """
        SELECT DISTINCT tp.name
        FROM touristicplace tp
        WHERE to_tsvector('polish', tp.name) @@ plainto_tsquery('polish', :query)
        ORDER BY ts_rank(to_tsvector('polish', tp.name), plainto_tsquery('polish', :query)) DESC
        LIMIT 6;
    """, nativeQuery = true)
    List<String> findForAutocomplete(@Param("query") final String query);

    Optional<TouristicPlaceEntity> findByPremiumId(final Long premiumId);

    Optional<TouristicPlaceEntity> findByAddressId(final Long addressId);
}
