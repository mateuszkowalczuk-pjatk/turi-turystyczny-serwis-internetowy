package com.turi.stay.infrastructure.adapter.interfaces;

import com.turi.infrastructure.exception.BadRequestResponseException;
import com.turi.stay.domain.model.StayDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StayResponse
{
    public static ResponseEntity<List<StayDto>> of(final List<StayDto> stays)
    {
        if (stays == null)
        {
            throw new BadRequestResponseException("Stays response must not be null.");
        }

        return ResponseEntity.ok(stays);
    }

    public static ResponseEntity<StayDto> of(final StayDto stay)
    {
        if (stay == null)
        {
            throw new BadRequestResponseException("Stay response must not be null.");
        }

        return ResponseEntity.ok(stay);
    }
}
