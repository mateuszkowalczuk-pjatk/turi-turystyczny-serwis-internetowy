package com.turi.stay.infrastructure.adapter.repository;

import com.turi.stay.domain.exception.InvalidStayException;
import com.turi.stay.domain.model.Stay;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stay")
@Builder(setterPrefix = "with")
public final class StayEntity implements Serializable
{
    @Serial
    private static final long serialVersionUID = 1780611039857976157L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stayid")
    private Long stayId;

    @Column(name = "touristicplaceid", nullable = false)
    private Long touristicPlaceId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "peoplenumber", nullable = false)
    private Integer peopleNumber;

    @Column(name = "datefrom", nullable = false)
    private LocalDate dateFrom;

    @Column(name = "dateto", nullable = false)
    private LocalDate dateTo;

    public static StayEntity of(final Stay stay)
    {
        if (!validation(stay))
        {
            throw new InvalidStayException();
        }

        return StayEntity.builder()
                .withTouristicPlaceId(stay.getTouristicPlaceId())
                .withName(stay.getName())
                .withDescription(stay.getDescription())
                .withPrice(stay.getPrice())
                .withPeopleNumber(stay.getPeopleNumber())
                .withDateFrom(stay.getDateFrom())
                .withDateTo(stay.getDateTo())
                .build();
    }

    private static boolean validation(final Stay stay)
    {
        return stay.getTouristicPlaceId() != null
                && stay.getName() != null
                && stay.getDescription() != null
                && stay.getPrice() != null
                && stay.getPeopleNumber() != null
                && stay.getDateFrom() != null;
    }
}
