package com.turi.stay.infrastructure.adapter.interfaces;

import com.turi.infrastructure.common.ObjectId;
import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.stay.domain.model.Stay;
import com.turi.stay.domain.model.StayDto;
import com.turi.stay.domain.model.StayInformation;
import com.turi.stay.domain.port.StayService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class StayFacade
{
    private final StayService service;

    public ResponseEntity<List<StayDto>> getAllStaysByTouristicPlaceId(final String touristicPlaceId)
    {
        if (touristicPlaceId == null)
        {
            throw new BadRequestParameterException("Parameter touristicPlaceId must not be null.");
        }

        final var id = ObjectId.of(touristicPlaceId).getValue();

        return StayResponse.of(service.getAllByTouristicPlaceId(id));
    }

    public ResponseEntity<StayDto> getStayById(final String id)
    {
        if (id == null)
        {
            throw new BadRequestParameterException("Parameter ID must not be null.");
        }

        final var stayId = ObjectId.of(id).getValue();

        return StayResponse.of(service.getById(stayId));
    }

    public ResponseEntity<?> createStay(final StayDto stay)
    {
        if (stay == null)
        {
            throw new BadRequestParameterException("Parameter stay must not be null.");
        }

        service.create(stay);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> createStayInformation(final StayInformation stayInformation)
    {
        if (stayInformation == null)
        {
            throw new BadRequestParameterException("Parameter stayInformation must not be null.");
        }

        service.createStayInformation(stayInformation);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> updateStay(final String id, final Stay stay)
    {
        if (id == null || stay == null)
        {
            throw new BadRequestParameterException("Parameters ID and stay must not be null.");
        }

        final var stayId = ObjectId.of(id).getValue();

        service.update(stayId, stay);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> deleteStay(final String id)
    {
        if (id == null)
        {
            throw new BadRequestParameterException("Parameter ID must not be null.");
        }

        final var stayId = ObjectId.of(id).getValue();

        service.delete(stayId);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> deleteStayInformation(final String stayInformationId)
    {
        if (stayInformationId == null)
        {
            throw new BadRequestParameterException("Parameter stayInformationId must not be null.");
        }

        final var id = ObjectId.of(stayInformationId).getValue();

        service.deleteStayInformation(id);

        return ResponseEntity.ok().build();
    }
}
