package com.turi.premium.domain.model;

import com.turi.premium.infrastructure.adapter.repository.PremiumEntity;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public final class Premium
{
    private Long premiumId;
    private Long accountid;
    private String companyName;
    private String nip;
    private String bankAccountNumber;
    private LocalDate buyDate;
    private LocalDate expiresDate;
    private PremiumStatus status;

    public static Premium of(final PremiumEntity entity)
    {
        return Premium.builder()
                .withPremiumId(entity.getPremiumId())
                .withAccountid(entity.getAccountid())
                .withCompanyName(entity.getCompanyName())
                .withNip(entity.getNip())
                .withBankAccountNumber(entity.getBankAccountNumber())
                .withBuyDate(entity.getBuyDate())
                .withExpiresDate(entity.getExpiresDate())
                .withStatus(PremiumStatus.fromValue(entity.getStatus()))
                .build();
    }
}
