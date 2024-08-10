package com.turi.authentication.domain.model;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Builder(setterPrefix = "with")
public final class Authentication
{
    private String token;
    private Long expiresIn;
}
