package com.turi.touristicplace.infrastructure.adapter.repository;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

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
}
