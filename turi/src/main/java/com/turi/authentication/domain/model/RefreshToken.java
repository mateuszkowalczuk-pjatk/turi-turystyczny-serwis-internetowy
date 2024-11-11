package com.turi.authentication.domain.model;

import com.turi.authentication.infrastructure.adapter.repository.RefreshTokenEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@Builder(setterPrefix = "with")
public final class RefreshToken
{
    private Long refreshTokenId;
    private Long userId;
    private String token;
    private LocalDateTime expiresAt;

    public static RefreshToken of(final RefreshTokenEntity entity)
    {
        return RefreshToken.builder()
                .withRefreshTokenId(entity.getRefreshTokenId())
                .withUserId(entity.getUserId())
                .withToken(entity.getToken())
                .withExpiresAt(entity.getExpiresAt())
                .build();
    }
}
