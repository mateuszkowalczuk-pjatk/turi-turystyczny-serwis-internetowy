package com.turi.offer.infrastructure.adapter.repository;

import jakarta.persistence.Column;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public final class FavouriteId implements Serializable
{
    @Serial
    private static final long serialVersionUID = 2957677057618804183L;

    @Column(name = "accountid", nullable = false)
    private Long accountId;

    @Column(name = "touristicplaceid", nullable = false)
    private Long touristicPlaceId;
}
