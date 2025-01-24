package com.turi.attraction.infrastructure.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttractionRepositoryDao extends JpaRepository<AttractionEntity, Long>
{
    @Query(value = """
    SELECT name FROM (
        SELECT DISTINCT a.name, ts_rank(to_tsvector('simple', a.name), plainto_tsquery('simple', :query)) AS rank
        FROM attraction a
        WHERE to_tsvector('simple', a.name) @@ plainto_tsquery('simple', :query) OR a.name ILIKE '%' || :query || '%'
        ORDER BY rank DESC NULLS LAST
        LIMIT 5
    ) AS subquery;
    """, nativeQuery = true)
    List<String> findForAutocomplete(@Param("query") final String query);

    List<AttractionEntity> findAllByTouristicPlaceId(final Long touristicPlaceId);
}
