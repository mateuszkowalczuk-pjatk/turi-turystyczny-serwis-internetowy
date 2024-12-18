package com.turi.image.infrastructure.adapter.repository;

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
}
