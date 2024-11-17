package com.turi.premium.infrastructure.adapter.repository;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "premium")
@Builder(setterPrefix = "with")
public final class PremiumEntity implements Serializable
{
    @Serial
    private static final long serialVersionUID = 8649310190433661567L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "premiumid")
    private Long premiumId;
}
