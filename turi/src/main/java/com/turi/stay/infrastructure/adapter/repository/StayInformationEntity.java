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
@Table(name = "stayinformation")
@Builder(setterPrefix = "with")
public final class StayInformationEntity implements Serializable
{
    @Serial
    private static final long serialVersionUID = -758711128275243769L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stayinformationid")
    private Long stayInformationId;
}
