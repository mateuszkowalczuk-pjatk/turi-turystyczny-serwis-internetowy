package com.turi.attraction.infrastructure.adapter.repository;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

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
}
