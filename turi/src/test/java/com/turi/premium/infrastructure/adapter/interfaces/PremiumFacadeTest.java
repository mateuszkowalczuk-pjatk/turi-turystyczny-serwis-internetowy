package com.turi.premium.infrastructure.adapter.interfaces;

import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.infrastructure.exception.BadRequestResponseException;
import com.turi.payment.domain.model.PaymentMethod;
import com.turi.premium.domain.model.Premium;
import com.turi.premium.domain.model.PremiumCompanyParam;
import com.turi.premium.domain.model.PremiumStatus;
import com.turi.premium.domain.model.PremiumVerifyParam;
import com.turi.premium.infrastructure.config.PremiumProperties;
import com.turi.testhelper.annotation.RestControllerTest;
import com.turi.testhelper.utils.ContextHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RestControllerTest
class PremiumFacadeTest
{
    @Autowired(required = false)
    private PremiumFacade facade;

    @Autowired(required = false)
    private PremiumProperties premiumProperties;

    @Test
    void testPremium_GetPremiumOffer()
    {
        final var offer = facade.getPremiumOffer().getBody();

        assertNotNull(offer);
        assertThat(offer.getPrice()).isEqualTo(premiumProperties.getPrice());
        assertThat(offer.getLength()).isEqualTo(premiumProperties.getLength());
    }

    @Test
    void testPremium_GetPremiumByAccount()
    {
        final var premium = mockPremium();

        ContextHelper.setContextUserId(mockPremium().getAccountId());

        final var result = facade.getPremiumByAccount().getBody();

        assertNotNull(result);
        assertThat(result).isEqualTo(premium);
    }

    @Test
    void testPremium_GetPremiumByAccount_NotFound()
    {
        ContextHelper.setContextUserId(mockNewPremium().getAccountId());

        assertThrows(BadRequestResponseException.class, () -> facade.getPremiumByAccount());
    }

    @Test
    void testPremium_GetPremiumByAccount_ContextAccountIdIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.getPremiumByAccount());
    }

    @Test
    void testPremium_IsPremiumExistsForAccount_Exists()
    {
        ContextHelper.setContextUserId(mockPremium().getAccountId());

        final var result = facade.isPremiumExistsForAccount().getBody();

        assertNotNull(result);
        assertTrue(result);
    }

    @Test
    void testPremium_IsPremiumExistsForAccount_NotExists()
    {
        ContextHelper.setContextUserId(mockNewPremium().getAccountId());

        final var result = facade.isPremiumExistsForAccount().getBody();

        assertNotNull(result);
        assertFalse(result);
    }

    @Test
    void testPremium_IsPremiumExistsForAccount_ContextAccountIdIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.isPremiumExistsForAccount());
    }

    @Test
    void testPremium_CheckPaymentForPayment_ContextAccountIdIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.checkPaymentForPremium());
    }

    @Test
    void testPremium_SendPremiumLoginCode_WithoutRequiredAccountIdField()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.sendPremiumLoginCode(null, "janek@janek.pl"));
    }

    @Test
    void testPremium_SendPremiumLoginCode_WithoutRequiredEmailField()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.sendPremiumLoginCode(1L, null));
    }

    @Test
    void testPremium_VerifyPremium_WithoutRequiredFirstNameField()
    {
        final var params = PremiumVerifyParam.builder()
                .withLastName("Kowalski")
                .withCompanyName("Janex")
                .withNip("12345678901")
                .withBankAccountNumber("42424242424242424242424242")
                .build();

        assertThrows(BadRequestParameterException.class, () -> facade.verifyPremium(params));
    }

    @Test
    void testPremium_VerifyPremium_WithoutRequiredLastNameField()
    {
        final var params = PremiumVerifyParam.builder()
                .withFirstName("Jan")
                .withCompanyName("Janex")
                .withNip("12345678901")
                .withBankAccountNumber("42424242424242424242424242")
                .build();

        assertThrows(BadRequestParameterException.class, () -> facade.verifyPremium(params));
    }

    @Test
    void testPremium_VerifyPremium_WithoutRequiredCompanyNameField()
    {
        final var params = PremiumVerifyParam.builder()
                .withFirstName("Jan")
                .withLastName("Kowalski")
                .withNip("12345678901")
                .withBankAccountNumber("42424242424242424242424242")
                .build();

        assertThrows(BadRequestParameterException.class, () -> facade.verifyPremium(params));
    }

    @Test
    void testPremium_VerifyPremium_WithoutRequiredNipField()
    {
        final var params = PremiumVerifyParam.builder()
                .withFirstName("Jan")
                .withLastName("Kowalski")
                .withCompanyName("Janex")
                .withBankAccountNumber("42424242424242424242424242")
                .build();

        assertThrows(BadRequestParameterException.class, () -> facade.verifyPremium(params));
    }

    @Test
    void testPremium_VerifyPremium_WithoutRequireBankAccountNumberField()
    {
        final var params = PremiumVerifyParam.builder()
                .withFirstName("Jan")
                .withLastName("Kowalski")
                .withCompanyName("Janex")
                .withNip("12345678901")
                .build();

        assertThrows(BadRequestParameterException.class, () -> facade.verifyPremium(params));
    }

    @Test
    void testPremium_VerifyPremium_InvalidNipField()
    {
        final var params = PremiumVerifyParam.builder()
                .withFirstName("Jan")
                .withLastName("Kowalski")
                .withCompanyName("Janex")
                .withNip("123456789")
                .withBankAccountNumber("42424242424242424242424242")
                .build();

        assertThrows(BadRequestParameterException.class, () -> facade.verifyPremium(params));
    }

    @Test
    void testPremium_VerifyPremium_InvalidBankAccountNumberField()
    {
        final var params = PremiumVerifyParam.builder()
                .withFirstName("Jan")
                .withLastName("Kowalski")
                .withCompanyName("Janex")
                .withNip("12345678901")
                .withBankAccountNumber("424242424242424242424242")
                .build();

        assertThrows(BadRequestParameterException.class, () -> facade.verifyPremium(params));
    }

    @Test
    void testPremium_VerifyPremium_ContextAccountIdIsNull()
    {
        final var params = PremiumVerifyParam.builder()
                .withFirstName("Jan")
                .withLastName("Kowalski")
                .withCompanyName("Janex")
                .withNip("12345678901")
                .withBankAccountNumber("42424242424242424242424242")
                .build();

        assertThrows(BadRequestParameterException.class, () -> facade.verifyPremium(params));
    }

    @Test
    void testPremium_loginIntoPremiumAccount_WithoutRequiredLoginTokenField()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.loginIntoPremiumAccount(null, 111111));
    }

    @Test
    void testPremium_loginIntoPremiumAccount_WithoutRequiredCodeField()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.loginIntoPremiumAccount("sample-login-token", null));
    }

    @Test
    void testPremium_PayForPremium_WithoutRequiredMethodField()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.payForPremium(null));
    }

    @Test
    void testPremium_PayForPremium_ContextAccountIdIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.payForPremium(PaymentMethod.CARD));
    }

    @Test
    void testPremium_RenewPremium_WithoutRequiredMethodField()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.renewPremium(null));
    }

    @Test
    void testPremium_RenewPremium_ContextAccountIdIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.renewPremium(PaymentMethod.CARD));
    }

    @Test
    void testPremium_CancelPremium_ContextAccountIdIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.cancelPremium());
    }

    @Test
    void testPremium_UpdatePremiumCompanyDetails_WithoutRequiredCompanyNameField()
    {
        final var params = PremiumCompanyParam.builder()
                .withNip("12345678901")
                .withBankAccountNumber("42424242424242424242424242")
                .build();

        assertThrows(BadRequestParameterException.class, () -> facade.updatePremiumCompanyDetails(params));
    }

    @Test
    void testPremium_UpdatePremiumCompanyDetails_WithoutRequiredNipField()
    {
        final var params = PremiumCompanyParam.builder()
                .withCompanyName("Janex")
                .withBankAccountNumber("42424242424242424242424242")
                .build();

        assertThrows(BadRequestParameterException.class, () -> facade.updatePremiumCompanyDetails(params));
    }

    @Test
    void testPremium_UpdatePremiumCompanyDetails_WithoutRequireBankAccountNumberField()
    {
        final var params = PremiumCompanyParam.builder()
                .withCompanyName("Janex")
                .withNip("12345678901")
                .build();

        assertThrows(BadRequestParameterException.class, () -> facade.updatePremiumCompanyDetails(params));
    }

    @Test
    void testPremium_UpdatePremiumCompanyDetails_ContextAccountIdIsNull()
    {
        final var params = PremiumCompanyParam.builder()
                .withCompanyName("JanBud")
                .withNip("12345678901")
                .withBankAccountNumber("42424242424242424242424242")
                .build();

        assertThrows(BadRequestParameterException.class, () -> facade.updatePremiumCompanyDetails(params));
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
