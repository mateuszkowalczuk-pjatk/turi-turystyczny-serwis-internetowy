package com.turi.authentication.domain.model;

import lombok.*;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@Builder(setterPrefix = "with")
public final class Authentication
{
    private String accessToken;
    private String refreshToken;
    private String loginToken;
    private Long accessTokenExpiresIn;
    private Long refreshTokenExpiresIn;
}
