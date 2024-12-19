package com.turi.touristicplace.infrastructure.adapter.repository;

import com.turi.touristicplace.domain.exception.InvalidTouristicPlaceException;
import com.turi.touristicplace.domain.model.TouristicPlace;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalTime;

import static com.turi.touristicplace.domain.model.TouristicPlaceType.getValueOrDefault;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "touristicplace")
@Builder(setterPrefix = "with")
public final class TouristicPlaceEntity implements Serializable
{
    @Serial
    private static final long serialVersionUID = 7339411706329177583L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "touristicplaceid")
    private Long touristicPlaceId;

    @Column(name = "premiumid", nullable = false, unique = true)
    private Long premiumId;

    @Column(name = "addressid", unique = true)
    private Long addressId;

    @Column(name = "touristicplacetype")
    private int touristicPlaceType;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "information")
    private String information;

    @Column(name = "ownerdescription")
    private String ownerDescription;

    @Column(name = "checkintimefrom")
    private LocalTime checkInTimeFrom;

    @Column(name = "checkintimeto")
    private LocalTime checkInTimeTo;

    @Column(name = "checkouttimefrom")
    private LocalTime checkOutTimeFrom;

    @Column(name = "checkouttimeto")
    private LocalTime checkOutTimeTo;

    @Column(name = "prepayment")
    private boolean prepayment;

    @Column(name = "cancelreservationdays")
    private int cancelReservationDays;

    public static TouristicPlaceEntity of(final TouristicPlace touristicPlace)
    {
        if (!validation(touristicPlace))
        {
            throw new InvalidTouristicPlaceException();
        }

        return TouristicPlaceEntity.builder()
                .withPremiumId(touristicPlace.getPremiumId())
                .withAddressId(touristicPlace.getAddressId())
                .withTouristicPlaceType(getValueOrDefault(touristicPlace.getTouristicPlaceType()))
                .withName(touristicPlace.getName())
                .withDescription(touristicPlace.getDescription())
                .withInformation(touristicPlace.getInformation())
                .withOwnerDescription(touristicPlace.getOwnerDescription())
                .withCheckInTimeFrom(touristicPlace.getCheckInTimeFrom())
                .withCheckInTimeTo(touristicPlace.getCheckInTimeTo())
                .withCheckOutTimeFrom(touristicPlace.getCheckOutTimeFrom())
                .withCheckOutTimeTo(touristicPlace.getCheckOutTimeTo())
                .withPrepayment(touristicPlace.isPrepayment())
                .withCancelReservationDays(touristicPlace.getCancelReservationDays())
                .build();
    }

    private static boolean validation(final TouristicPlace touristicPlace)
    {
        return touristicPlace.getPremiumId() != null;
    }
}
