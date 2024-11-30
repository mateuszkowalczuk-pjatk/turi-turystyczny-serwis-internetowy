package com.turi.premium.domain.model;

import com.turi.address.domain.model.Address;
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
    private Address address;
    private String nip;
    private String bankAccountNumber;
}
