package com.turi.premium.domain.model;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public final class PremiumCompanyParam
{
    private String companyName;
    private String nip;
    private String bankAccountNumber;
}
