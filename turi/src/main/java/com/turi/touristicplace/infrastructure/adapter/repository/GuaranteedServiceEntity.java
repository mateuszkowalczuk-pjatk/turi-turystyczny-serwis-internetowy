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
}
