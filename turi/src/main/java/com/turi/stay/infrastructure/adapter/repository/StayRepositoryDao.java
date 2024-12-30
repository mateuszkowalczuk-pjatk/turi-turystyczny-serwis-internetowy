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
        SELECT DISTINCT s.name
        FROM stay s
        WHERE to_tsvector('polish', s.name) @@ plainto_tsquery('polish', :query)
        ORDER BY ts_rank(to_tsvector('polish', s.name), plainto_tsquery('polish', :query)) DESC
        LIMIT 6;
    """, nativeQuery = true)
    List<String> findForAutocomplete(@Param("query") final String query);

    List<StayEntity> findAllByTouristicPlaceId(final Long touristicPlaceId);
}
