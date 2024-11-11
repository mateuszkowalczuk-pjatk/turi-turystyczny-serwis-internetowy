package com.turi.authentication.domain.model;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder(setterPrefix = "with")
public final class RefreshParam
{
    private String refreshToken;
}
