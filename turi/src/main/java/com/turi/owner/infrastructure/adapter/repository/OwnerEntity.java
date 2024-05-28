package com.turi.owner.infrastructure.adapter.repository;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "owner")
@Builder(setterPrefix = "with")
public final class OwnerEntity implements Serializable
{
    @Serial
    private static final long serialVersionUID = -6815925214521941740L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ownerid")
    private Long ownerId;

    @Column(name = "firstname", length = 50, nullable = false)
    private String firstName;

    @Column(name = "lastname", length = 50, nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String description;

    @Column(name = "phonenumber", length = 20, nullable = false)
    private String phoneNumber;

    @Column(length = 30, nullable = false)
    private String email;
}
