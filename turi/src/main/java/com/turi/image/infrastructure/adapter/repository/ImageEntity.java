package com.turi.image.infrastructure.adapter.repository;

import com.turi.image.domain.exception.InvalidImageException;
import com.turi.image.domain.model.Image;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "image")
@Builder(setterPrefix = "with")
public final class ImageEntity implements Serializable
{
    @Serial
    private static final long serialVersionUID = 2519212711277159486L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "imageid")
    private Long imageId;

    @Column(name = "accountid")
    private Long accountId;

    @Column(name = "touristicplaceid")
    private Long touristicPlaceId;

    @Column(name = "stayid")
    private Long stayId;

    @Column(name = "attractionid")
    private Long attractionId;

    @Column(name = "path", nullable = false)
    private String path;

    public static ImageEntity of(final Image image)
    {
        if (!validation(image))
        {
            throw new InvalidImageException();
        }

        return ImageEntity.builder()
                .withAccountId(image.getAccountId())
                .withTouristicPlaceId(image.getTouristicPlaceId())
                .withStayId(image.getStayId())
                .withAttractionId(image.getAttractionId())
                .withPath(image.getPath())
                .build();
    }

    private static boolean validation(final Image image)
    {
        return image.getPath() != null;
    }
}
