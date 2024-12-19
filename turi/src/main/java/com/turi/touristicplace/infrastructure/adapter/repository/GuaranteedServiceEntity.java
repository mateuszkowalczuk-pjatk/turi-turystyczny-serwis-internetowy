package com.turi.touristicplace.infrastructure.adapter.repository;

import com.turi.touristicplace.domain.exception.InvalidGuaranteedServiceException;
import com.turi.touristicplace.domain.model.GuaranteedService;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "guaranteedservice")
@Builder(setterPrefix = "with")
public final class GuaranteedServiceEntity implements Serializable
{
    @Serial
    private static final long serialVersionUID = -1009125219841492537L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guaranteedserviceid")
    private Long guaranteedServiceId;

    @Column(name = "touristicplaceid", nullable = false)
    private Long touristicPlaceId;

    @Column(name = "service", nullable = false)
    private String service;

    public static GuaranteedServiceEntity of(final GuaranteedService guaranteedService)
    {
        if (!validation(guaranteedService))
        {
            throw new InvalidGuaranteedServiceException();
        }

        return GuaranteedServiceEntity.builder()
                .withTouristicPlaceId(guaranteedService.getTouristicPlaceId())
                .withService(guaranteedService.getService())
                .build();
    }

    private static boolean validation(final GuaranteedService guaranteedService)
    {
        return guaranteedService.getTouristicPlaceId() != null && guaranteedService.getService() != null;
    }
}
