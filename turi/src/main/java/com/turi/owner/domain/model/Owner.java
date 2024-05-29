package com.turi.owner.domain.model;

import com.turi.owner.infrastructure.adapter.repository.OwnerEntity;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Builder(setterPrefix = "with")
public final class Owner
{
    private Long ownerId;
    private String firstName;
    private String lastName;
    private String description;
    private String phoneNumber;
    private String email;

    public static Owner of(final OwnerEntity entity)
    {
        return Owner.builder()
                .withOwnerId(entity.getOwnerId())
                .withFirstName(entity.getFirstName())
                .withLastName(entity.getLastName())
                .withDescription(entity.getDescription())
                .withPhoneNumber(entity.getPhoneNumber())
                .withEmail(entity.getEmail())
                .build();
    }
}
