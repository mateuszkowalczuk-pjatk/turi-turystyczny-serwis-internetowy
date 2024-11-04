package com.turi.authentication.infrastructure.adapter.repository;

import com.turi.authentication.domain.exception.InvalidRefreshTokenException;
import com.turi.authentication.domain.model.RefreshToken;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "refreshtoken")
@Builder(setterPrefix = "with")
public final class RefreshTokenEntity implements Serializable
{
    @Serial
    private static final long serialVersionUID = -1913410603537784814L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refreshtokenid")
    private Long refreshTokenId;

    @Column(name = "userid", nullable = false)
    private Long userId;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "expiresat", nullable = false)
    private LocalDateTime expiresAt;

    public static RefreshTokenEntity of(final RefreshToken refreshToken)
    {
        if (!validation(refreshToken))
        {
            throw new InvalidRefreshTokenException();
        }

        return RefreshTokenEntity.builder()
                .withUserId(refreshToken.getUserId())
                .withToken(refreshToken.getToken())
                .withExpiresAt(refreshToken.getExpiresAt())
                .build();
    }

    private static boolean validation(final RefreshToken refreshToken)
    {
        return refreshToken.getUserId() != null
                && refreshToken.getToken() != null
                && refreshToken.getExpiresAt() != null;
    }
}
