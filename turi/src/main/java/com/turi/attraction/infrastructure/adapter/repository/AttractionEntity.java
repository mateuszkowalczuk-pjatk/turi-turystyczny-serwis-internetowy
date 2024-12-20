package com.turi.attraction.infrastructure.adapter.repository;

import com.turi.attraction.domain.exception.InvalidAttractionException;
import com.turi.attraction.domain.model.Attraction;
import com.turi.attraction.domain.model.AttractionType;
import com.turi.attraction.domain.model.PriceType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "attraction")
@Builder(setterPrefix = "with")
public final class AttractionEntity implements Serializable
{
    @Serial
    private static final long serialVersionUID = -270072735443049012L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attractionid")
    private Long attractionId;

    @Column(name = "touristicplaceid", nullable = false)
    private Long touristicPlaceId;

    @Column(name = "attractiontype", nullable = false)
    private int attractionType;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "pricetype", nullable = false)
    private int priceType;

    @Column(name = "prepayment", nullable = false)
    private Boolean prepayment;

    @Column(name = "cancelreservationdays")
    private Integer cancelReservationDays;

    @Column(name = "maxpeoplenumber")
    private Integer maxPeopleNumber;

    @Column(name = "maxitems")
    private Integer maxItems;

    @Column(name = "datefrom", nullable = false)
    private LocalDate dateFrom;

    @Column(name = "dateto", nullable = false)
    private LocalDate dateTo;

    @Column(name = "hourfrom", nullable = false)
    private LocalTime hourFrom;

    @Column(name = "hourto", nullable = false)
    private LocalTime hourTo;

    @Column(name = "daysreservationbefore", nullable = false)
    private Integer daysReservationBefore;

    public static AttractionEntity of(final Attraction attraction)
    {
        if (!validation(attraction))
        {
            throw new InvalidAttractionException();
        }

        return AttractionEntity.builder()
                .withTouristicPlaceId(attraction.getTouristicPlaceId())
                .withAttractionType(AttractionType.getValueOrDefault(attraction.getAttractionType()))
                .withName(attraction.getName())
                .withDescription(attraction.getDescription())
                .withPrice(attraction.getPrice())
                .withPriceType(PriceType.getValueOrDefault(attraction.getPriceType()))
                .withPrepayment(attraction.getPrepayment())
                .withCancelReservationDays(attraction.getCancelReservationDays())
                .withMaxPeopleNumber(attraction.getMaxPeopleNumber())
                .withMaxItems(attraction.getMaxItems())
                .withDateFrom(attraction.getDateFrom())
                .withDateTo(attraction.getDateTo())
                .withHourFrom(attraction.getHourFrom())
                .withHourTo(attraction.getHourTo())
                .withDaysReservationBefore(attraction.getDaysReservationBefore())
                .build();
    }

    private static boolean validation(final Attraction attraction)
    {
        return attraction.getTouristicPlaceId() != null
                && attraction.getAttractionType() != null
                && attraction.getName() != null
                && attraction.getDescription() != null
                && attraction.getPrice() != null
                && attraction.getPriceType() != null
                && attraction.getPrepayment() != null
                && attraction.getDateFrom() != null
                && attraction.getDateTo() != null
                && attraction.getHourFrom() != null
                && attraction.getHourTo() != null
                && attraction.getDaysReservationBefore() != null;
    }
}
