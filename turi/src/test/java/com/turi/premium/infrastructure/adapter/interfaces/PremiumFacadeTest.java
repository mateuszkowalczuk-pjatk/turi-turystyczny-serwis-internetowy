package com.turi.premium.infrastructure.adapter.interfaces;

import com.turi.infrastructure.properties.PremiumProperties;
import com.turi.premium.domain.model.Premium;
import com.turi.premium.domain.model.PremiumStatus;
import com.turi.testhelper.annotation.RestControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
//        final var offer = service.getOffer();
//
//        assertNotNull(offer);
//        assertThat(offer.getPrice()).isEqualTo(premiumProperties.getPrice());
//        assertThat(offer.getLength()).isEqualTo(premiumProperties.getLength());
    }

    @Test
    void testPremium_GetPremiumByAccount()
    {
//        final var premium = mockPremium();
//
//        final var result = service.getByAccount(premium.getAccountid());
//
//        assertNotNull(result);
//        assertThat(result).isEqualTo(premium);
    }

    @Test
    void testPremium_GetPremiumByAccount_NotFound()
    {
//        assertNull(service.getByAccount(mockNewPremium().getAccountid()));
    }

    @Test
    void testPremium_IsPremiumExistsForAccount_Exists()
    {
//        final var result = service.isExistsForAccount(mockPremium().getAccountid());
//
//        assertNotNull(result);
//        assertTrue(result);
    }

    @Test
    void testPremium_IsPremiumExistsForAccount_NotExists()
    {
//        final var result = service.isExistsForAccount(mockNewPremium().getAccountid());
//
//        assertNotNull(result);
//        assertFalse(result);
    }

    @Test
    void testPremium_CheckPaymentForPayment()
    {

    }

    @Test
    void testPremium_CheckPaymentForPayment_PremiumNotFound()
    {

    }

    @Test
    void testPremium_CheckPaymentForPayment_PremiumUnpaid()
    {

    }

    @Test
    void testPremium_SendPremiumLoginCode()
    {

    }

    @Test
    void testPremium_SendPremiumLoginCode_PremiumNotFound()
    {

    }

    @Test
    void testPremium_SendPremiumLoginCode_UserResetCodeRecentlySent()
    {

    }

    @Test
    void testPremium_SendPremiumLoginCode_WithoutRequiredAccountIdField()
    {

    }

    @Test
    void testPremium_SendPremiumLoginCode_WithoutRequiredEmailField()
    {

    }

    @Test
    void testPremium_VerifyPremium()
    {

    }

    @Test
    void testPremium_VerifyPremium_InvalidCompany()
    {

    }

    @Test
    void testPremium_VerifyPremium_WithoutRequiredFirstNameField()
    {

    }

    @Test
    void testPremium_VerifyPremium_WithoutRequiredLastNameField()
    {

    }

    @Test
    void testPremium_VerifyPremium_WithoutRequiredCompanyNameField()
    {

    }

    @Test
    void testPremium_VerifyPremium_WithoutRequiredAddressField()
    {

    }

    @Test
    void testPremium_VerifyPremium_WithoutRequiredAddressCountryField()
    {

    }

    @Test
    void testPremium_VerifyPremium_WithoutRequiredAddressCityField()
    {

    }

    @Test
    void testPremium_VerifyPremium_WithoutRequiredAddressZipCodeField()
    {

    }

    @Test
    void testPremium_VerifyPremium_WithoutRequiredAddressStreetField()
    {

    }

    @Test
    void testPremium_VerifyPremium_WithoutRequiredAddressBuildingNumberField()
    {

    }

    @Test
    void testPremium_VerifyPremium_WithoutRequiredNipField()
    {

    }

    @Test
    void testPremium_VerifyPremium_WithoutRequireBankAccountNumberField()
    {

    }

    @Test
    void testPremium_VerifyPremium_InvalidNipField()
    {

    }

    @Test
    void testPremium_VerifyPremium_InvalidBankAccountNumberField()
    {

    }

    @Test
    void testPremium_loginIntoPremiumAccount()
    {

    }

    @Test
    void testPremium_loginIntoPremiumAccount_PremiumNotFound()
    {

    }

    @Test
    void testPremium_loginIntoPremiumAccount_InvalidCode()
    {

    }

    @Test
    void testPremium_loginIntoPremiumAccount_CodeExpired()
    {

    }

    @Test
    void testPremium_loginIntoPremiumAccount_WithoutRequiredLoginTokenField()
    {

    }

    @Test
    void testPremium_loginIntoPremiumAccount_WithoutRequiredCodeField()
    {

    }

    @Test
    void testPremium_PayForPremium()
    {

    }

    @Test
    void testPremium_PayForPremium_PremiumAlreadyActivated()
    {

    }

    @Test
    void testPremium_PayForPremium_WithoutRequiredMethodField()
    {

    }

    @Test
    void testPremium_RenewPremium()
    {

    }

    @Test
    void testPremium_RenewPremium_PremiumAlreadyActivated()
    {

    }

    @Test
    void testPremium_RenewPremium_WithoutRequiredMethodField()
    {

    }

    @Test
    void testPremium_CancelPremium()
    {

    }

    @Test
    void testPremium_CancelPremium_Inactive()
    {

    }

    @Test
    void testPremium_UpdatePremiumCompanyDetails()
    {

    }

    @Test
    void testPremium_UpdatePremiumCompanyDetails_InvalidCompany()
    {

    }

    @Test
    void testPremium_UpdatePremiumCompanyDetails_WithoutRequiredCompanyNameField()
    {

    }

    @Test
    void testPremium_UpdatePremiumCompanyDetails_WithoutRequiredAddressField()
    {

    }

    @Test
    void testPremium_UpdatePremiumCompanyDetails_WithoutRequiredAddressCountryField()
    {

    }

    @Test
    void testPremium_UpdatePremiumCompanyDetails_WithoutRequiredAddressCityField()
    {

    }

    @Test
    void testPremium_UpdatePremiumCompanyDetails_WithoutRequiredAddressZipCodeField()
    {

    }

    @Test
    void testPremium_UpdatePremiumCompanyDetails_WithoutRequiredAddressStreetField()
    {

    }

    @Test
    void testPremium_UpdatePremiumCompanyDetails_WithoutRequiredAddressBuildingNumberField()
    {

    }

    @Test
    void testPremium_UpdatePremiumCompanyDetails_WithoutRequiredNipField()
    {

    }

    @Test
    void testPremium_UpdatePremiumCompanyDetails_WithoutRequireBankAccountNumberField()
    {

    }

    private Premium mockPremium()
    {
        return Premium.builder()
                .withPremiumId(1L)
                .withAccountid(2L)
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
                .withAccountid(3L)
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
