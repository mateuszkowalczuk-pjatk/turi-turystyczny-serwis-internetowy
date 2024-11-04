package com.turi.authentication.infrastructure.adapter.application.queries.refresh;

import lombok.*;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public final class RefreshParam
{
    private String refreshToken;
}
