package com.turi.touristicplace.domain.model;

import com.turi.touristicplace.infrastructure.adapter.repository.TouristicPlaceEntity;
import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public final class TouristicPlace
{
    private Long touristicPlaceId;
    private Long premiumId;
    private Long addressId;
    private TouristicPlaceType touristicPlaceType;
    private String name;
    private String description;
    private String information;
    private String ownerDescription;
    private LocalTime checkInTimeFrom;
    private LocalTime checkInTimeTo;
    private LocalTime checkOutTimeFrom;
    private LocalTime checkOutTimeTo;
    private boolean prepayment;
    private int cancelReservationDays;

    public static TouristicPlace of(final TouristicPlaceEntity entity)
    {
        return TouristicPlace.builder()
                .withTouristicPlaceId(entity.getTouristicPlaceId())
                .withPremiumId(entity.getPremiumId())
                .withAddressId(entity.getAddressId())
                .withTouristicPlaceType(TouristicPlaceType.fromValue(entity.getTouristicPlaceType()))
                .withName(entity.getName())
                .withDescription(entity.getDescription())
                .withInformation(entity.getInformation())
                .withOwnerDescription(entity.getOwnerDescription())
                .withCheckInTimeFrom(entity.getCheckInTimeFrom())
                .withCheckInTimeTo(entity.getCheckInTimeTo())
                .withCheckOutTimeFrom(entity.getCheckOutTimeFrom())
                .withCheckOutTimeTo(entity.getCheckOutTimeTo())
                .withPrepayment(entity.isPrepayment())
                .withCancelReservationDays(entity.getCancelReservationDays())
                .build();
    }
}
