package com.turi.account.infrastructure.adapter.repository;

import com.turi.account.domain.exception.InvalidAccountException;
import com.turi.account.domain.model.Account;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
@Builder(setterPrefix = "with")
public final class AccountEntity implements Serializable
{
    @Serial
    private static final long serialVersionUID = 1576802374960749581L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accountid")
    private Long accountId;

    @Column(name = "userid", unique = true)
    private Long userId;

    @Column(name = "addressid", unique = true)
    private Long addressId;

    @Column(name = "accounttype", nullable = false)
    private int accountType;

    @Column(name = "login", length = 50, nullable = false, unique = true)
    private String login;

    @Column(name = "email", length = 50, nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "firstname", length = 50)
    private String firstName;

    @Column(name = "lastname", length = 50)
    private String lastName;

    @Column(name = "birthdate")
    private LocalDate birthDate;

    @Column(name = "phonenumber", length = 20, unique = true)
    private String phoneNumber;

    @Column(name = "gender")
    private int gender;

    public static AccountEntity of(final Account account)
    {
        if (!validation(account))
        {
            throw new InvalidAccountException();
        }

        final var gender = account.getGender() != null ? account.getGender().getValue() : 0;

        return AccountEntity.builder()
                .withUserId(account.getUserId())
                .withAddressId(account.getAddressId())
                .withAccountType(account.getAccountType().getValue())
                .withFirstName(account.getFirstName())
                .withLastName(account.getLastName())
                .withBirthDate(account.getBirthDate())
                .withPhoneNumber(account.getPhoneNumber())
                .withGender(gender)
                .build();
    }

    private static boolean validation(final Account account)
    {
        return account.getUserId() == null && account.getAccountType() != null;
    }
}
