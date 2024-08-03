package com.turi.account.domain.model;

import com.turi.account.infrastructure.adapter.repository.AccountEntity;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@Builder(setterPrefix = "with")
public final class Account
{
    private Long accountId;
    private Long addressId;
    private AccountType accountType;
    private String login;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String phoneNumber;
    private Gender gender;

    public static Account of(final AccountEntity entity)
    {
        return Account.builder()
                .withAccountId(entity.getAccountId())
                .withAddressId(entity.getAddressId())
                .withAccountType(AccountType.fromValue(entity.getAccountType()))
                .withLogin(entity.getLogin())
                .withEmail(entity.getEmail())
                .withPassword(entity.getPassword())
                .withFirstName(entity.getFirstName())
                .withLastName(entity.getLastName())
                .withBirthDate(entity.getBirthDate())
                .withPhoneNumber(entity.getPhoneNumber())
                .withGender(Gender.fromValue(entity.getGender()))
                .build();
    }
}
