package com.turi.search.infrastructure.adapter.interfaces;

import com.turi.infrastructure.exception.BadRequestResponseException;
import com.turi.search.domain.model.Search;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchResponse
{
    public static ResponseEntity<Search> of(final Search search)
    {
        if (search == null)
        {
            throw new BadRequestResponseException("Search response must not be null");
        }

        return ResponseEntity.ok(search);
    }

    public static ResponseEntity<List<String>> of(final List<String> autocomplete)
    {
        if (autocomplete == null)
        {
            throw new BadRequestResponseException("Autocomplete response must not be null");
        }

        return ResponseEntity.ok(autocomplete);
    }
}
