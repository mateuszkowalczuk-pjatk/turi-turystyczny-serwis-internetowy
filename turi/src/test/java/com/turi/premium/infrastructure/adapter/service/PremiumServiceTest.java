package com.turi.premium.infrastructure.adapter.service;

import com.turi.premium.domain.exception.PremiumNotFoundException;
import com.turi.premium.domain.model.Premium;
import com.turi.premium.domain.model.PremiumStatus;
import com.turi.premium.domain.port.PremiumService;
import com.turi.premium.infrastructure.config.PremiumProperties;
import com.turi.testhelper.annotation.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ServiceTest
class PremiumServiceTest
{
    @Autowired
    private PremiumService service;

    @Autowired
    private PremiumProperties premiumProperties;

    @Test
    void testPremium_GetOffer()
    {
        final var offer = service.getOffer();

        assertNotNull(offer);
        assertThat(offer.getPrice()).isEqualTo(premiumProperties.getPrice());
        assertThat(offer.getLength()).isEqualTo(premiumProperties.getLength());
    }

    @Test
    void testPremium_GetById()
    {
        final var premium = mockPremium();

        final var result = service.getById(premium.getPremiumId());

        assertNotNull(result);
        assertThat(result).isEqualTo(premium);
    }

    @Test
    void testPremium_GetById_NotFound()
    {
        final var premium = mockNewPremium();

        assertThrows(PremiumNotFoundException.class, () -> service.getById(premium.getPremiumId()));
    }

    @Test
    void testPremium_GetByAccount()
    {
        final var premium = mockPremium();

        final var result = service.getByAccount(premium.getAccountId());

        assertNotNull(result);
        assertThat(result).isEqualTo(premium);
    }

    @Test
    void testPremium_GetByAccount_NotFound()
    {
        assertNull(service.getByAccount(mockNewPremium().getAccountId()));
    }

    @Test
    void testPremium_GetByLoginToken()
    {
        final var premium = mockPremium();

        final var result = service.getByLoginToken(premium.getLoginToken());

        assertNotNull(result);
        assertThat(result).isEqualTo(premium);
    }

    @Test
    void testPremium_GetByLoginToken_NotFound()
    {
        assertNull(service.getByLoginToken(mockNewPremium().getLoginToken()));
    }

    @Test
    void testPremium_IsExistsForAccount_Exists()
    {
        final var result = service.isExistsForAccount(mockPremium().getAccountId());

        assertNotNull(result);
        assertTrue(result);
    }

    @Test
    void testPremium_IsExistsForAccount_NotExists()
    {
        final var result = service.isExistsForAccount(mockNewPremium().getAccountId());

        assertNotNull(result);
        assertFalse(result);
    }

    private Premium mockPremium()
    {
        return Premium.builder()
                .withPremiumId(1L)
                .withAccountId(2L)
                .withCompanyName("Jarex")
                .withNip("1423456812")
                .withBankAccountNumber("120023321456120023321456")
                .withBuyDate(LocalDate.of(2024, 10, 10))
                .withExpiresDate(LocalDate.of(2025, 10, 10))
                .withStatus(PremiumStatus.ACTIVE)
                .withLoginCode(123456)
                .withLoginToken("sample-login-token")
                .withLoginExpiresAt(LocalDateTime.of(2024, 10, 10, 12, 0, 0))
                .build();
    }

    private Premium mockNewPremium()
    {
        return Premium.builder()
                .withPremiumId(2L)
                .withAccountId(3L)
                .withCompanyName("Marex")
                .withNip("1423456833")
                .withBankAccountNumber("120023321456120023321123")
                .withBuyDate(LocalDate.of(2024, 12, 10))
                .withExpiresDate(LocalDate.of(2025, 12, 10))
                .withStatus(PremiumStatus.ACTIVE)
                .withLoginCode(654321)
                .withLoginToken("sample-other-login-token")
                .withLoginExpiresAt(LocalDateTime.of(2024, 1, 10, 12, 0, 0))
                .build();
    }
}
