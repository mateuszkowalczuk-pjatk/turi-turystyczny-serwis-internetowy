package com.turi.stay.infrastructure.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StayRepositoryDao extends JpaRepository<StayEntity, Long>
{
    @Query(value = """
    SELECT name FROM (
        SELECT DISTINCT s.name, ts_rank(to_tsvector('simple', s.name), plainto_tsquery('simple', :query)) AS rank
        FROM stay s
        WHERE to_tsvector('simple', s.name) @@ plainto_tsquery('simple', :query) OR s.name ILIKE '%' || :query || '%'
        ORDER BY rank DESC NULLS LAST
        LIMIT 6
    ) AS subquery;
    """, nativeQuery = true)
    List<String> findForAutocomplete(@Param("query") final String query);

    List<StayEntity> findAllByTouristicPlaceId(final Long touristicPlaceId);
}
