package com.turi.premium.domain.model;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class PremiumLogin
{
    private String loginToken;
    private Long loginTokenExpiresIn;
}
