package com.turi.premium.domain.model;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public final class PremiumVerifyParam
{
    private String firstName;
    private String lastName;
    private String companyName;
    private String nip;
    private String bankAccountNumber;
}
