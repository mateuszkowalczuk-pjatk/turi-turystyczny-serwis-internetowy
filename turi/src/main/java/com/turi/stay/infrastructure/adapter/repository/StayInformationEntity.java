package com.turi.stay.infrastructure.adapter.repository;

import com.turi.stay.domain.exception.InvalidStayInformationException;
import com.turi.stay.domain.model.StayInformation;
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

    @Column(name = "stayid", nullable = false)
    private Long stayId;

    @Column(name = "information", nullable = false)
    private String information;

    public static StayInformationEntity of(final StayInformation stayInformation)
    {
        if (!validation(stayInformation))
        {
            throw new InvalidStayInformationException();
        }

        return StayInformationEntity.builder()
                .withStayId(stayInformation.getStayId())
                .withInformation(stayInformation.getInformation())
                .build();
    }

    private static boolean validation(final StayInformation stayInformation)
    {
        return stayInformation.getStayId() != null && stayInformation.getInformation() != null;
    }
}
