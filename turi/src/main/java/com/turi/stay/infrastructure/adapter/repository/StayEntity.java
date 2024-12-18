package com.turi.stay.infrastructure.adapter.repository;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

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
}
