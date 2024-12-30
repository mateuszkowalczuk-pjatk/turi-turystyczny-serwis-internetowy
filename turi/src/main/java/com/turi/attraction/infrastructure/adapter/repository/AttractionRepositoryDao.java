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
        SELECT DISTINCT a.name
        FROM attraction a
        WHERE to_tsvector('polish', a.name) @@ plainto_tsquery('polish', :query)
        ORDER BY ts_rank(to_tsvector('polish', a.name), plainto_tsquery('polish', :query)) DESC
        LIMIT 6;
    """, nativeQuery = true)
    List<String> findForAutocomplete(@Param("query") final String query);

    List<AttractionEntity> findAllByTouristicPlaceId(final Long touristicPlaceId);
}
