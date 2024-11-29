package com.turi.premium.infrastructure.adapter.repository;

import com.turi.premium.domain.exception.InvalidPremiumException;
import com.turi.premium.domain.model.Premium;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

import static com.turi.premium.domain.model.PremiumStatus.getValueOrDefault;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "premium")
@Builder(setterPrefix = "with")
public final class PremiumEntity implements Serializable
{
    @Serial
    private static final long serialVersionUID = 8649310190433661567L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "premiumid")
    private Long premiumId;

    @Column(name = "accountid", nullable = false, unique = true)
    private Long accountid;

    @Column(name = "companyname", nullable = false, unique = true)
    private String companyName;

    @Column(name = "nip", nullable = false, unique = true)
    private String nip;

    @Column(name = "bankaccountnumber", nullable = false, unique = true)
    private String bankAccountNumber;

    @Column(name = "buydate", nullable = false)
    private LocalDate buyDate;

    @Column(name = "expiresdate", nullable = false)
    private LocalDate expiresDate;

    @Column(name = "status", nullable = false)
    private int status;

    public static PremiumEntity of(final Premium premium)
    {
        if (!validation(premium))
        {
            throw new InvalidPremiumException();
        }

        return PremiumEntity.builder()
                .withAccountid(premium.getAccountid())
                .withCompanyName(premium.getCompanyName())
                .withNip(premium.getNip())
                .withBankAccountNumber(premium.getBankAccountNumber())
                .withBuyDate(premium.getBuyDate())
                .withExpiresDate(premium.getExpiresDate())
                .withStatus(getValueOrDefault(premium.getStatus()))
                .build();
    }

    private static boolean validation(final Premium premium)
    {
        return premium.getAccountid() != null
                && premium.getCompanyName() != null
                && premium.getNip() != null
                && premium.getBankAccountNumber() != null
                && premium.getBuyDate() != null
                && premium.getExpiresDate() != null
                && premium.getStatus() != null;
    }
}
