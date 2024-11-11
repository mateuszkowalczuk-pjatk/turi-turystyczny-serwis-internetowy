package com.turi.user.domain.model;

import lombok.*;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@Builder(setterPrefix = "with")
public final class ResetToken
{
    private String token;
    private Long expiresIn;
}
