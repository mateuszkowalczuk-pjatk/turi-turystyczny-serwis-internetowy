package com.turi.user.domain.model;

import lombok.*;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@Builder(setterPrefix = "with")
public final class RefreshToken
{
    private String refreshToken;
    private Long refreshTokenExpiresIn;
}