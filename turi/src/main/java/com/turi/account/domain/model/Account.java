package com.turi.account.domain.model;

import com.turi.account.infrastructure.adapter.repository.AccountEntity;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public final class Account
{
    private Long accountId;
    private Long userId;
    private Long addressId;
    private AccountType accountType;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String phoneNumber;
    private Gender gender;

    public static Account of(final AccountEntity entity)
    {
        return Account.builder()
                .withAccountId(entity.getAccountId())
                .withUserId(entity.getUserId())
                .withAddressId(entity.getAddressId())
                .withAccountType(AccountType.fromValue(entity.getAccountType()))
                .withFirstName(entity.getFirstName())
                .withLastName(entity.getLastName())
                .withBirthDate(entity.getBirthDate())
                .withPhoneNumber(entity.getPhoneNumber())
                .withGender(Gender.fromValue(entity.getGender()))
                .build();
    }
}
