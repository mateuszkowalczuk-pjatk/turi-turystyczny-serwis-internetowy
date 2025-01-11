package com.turi.premium.infrastructure.adapter.interfaces;

import com.turi.account.domain.model.AccountType;
import com.turi.authentication.domain.port.JwtService;
import com.turi.infrastructure.rest.ErrorCode;
import com.turi.premium.domain.model.*;
import com.turi.premium.infrastructure.config.PremiumProperties;
import com.turi.testhelper.annotation.RestControllerTest;
import com.turi.testhelper.rest.AbstractRestControllerIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@RestControllerTest
class PremiumRestControllerTest extends AbstractRestControllerIntegrationTest
{
    @Autowired(required = false)
    private JwtService jwtService;

    @Autowired(required = false)
    private PremiumProperties premiumProperties;

    @Test
    void testPremium_GetPremiumOffer()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/premium/getOffer")
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockPremium().getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), PremiumOffer.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertNotNull(result.getBody());
        assertThat(result.getBody().getPrice()).isEqualTo(premiumProperties.getPrice());
        assertThat(result.getBody().getLength()).isEqualTo(premiumProperties.getLength());
    }

    @Test
    void testPremium_GetPremiumByAccount()
    {
        final var premium = mockPremium();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/premium/getByAccount")
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(premium.getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), Premium.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertNotNull(result.getBody());
        assertThat(result.getBody()).isEqualTo(premium);
    }

    @Test
    void testPremium_GetPremiumByAccount_NotFound()
    {
        final var premium = mockNewPremium();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/premium/getByAccount")
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(premium.getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testPremium_GetPremiumByAccount_ContextAccountIdIsNull()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/premium/getByAccount")
                .build().toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testPremium_IsPremiumExistsForAccount_Exists()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/premium/isExistsForAccount")
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockPremium().getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), Boolean.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertNotNull(result.getBody());
        assertTrue(result.getBody());
    }

    @Test
    void testPremium_IsPremiumExistsForAccount_NotExists()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/premium/isExistsForAccount")
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockNewPremium().getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), Boolean.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertNotNull(result.getBody());
        assertFalse(result.getBody());
    }

    @Test
    void testPremium_IsPremiumExistsForAccount_ContextAccountIdIsNull()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/premium/isExistsForAccount")
                .build().toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
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
    void testPremium_CheckPaymentForPayment_ContextAccountIdIsNull()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/premium/checkPayment")
                .build().toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
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
        final var params = PremiumVerifyParam.builder()
                .withLastName("Kowalski")
                .withCompanyName("Janex")
                .withNip("12345678901")
                .withBankAccountNumber("42424242424242424242424242")
                .build();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/premium/verify")
                .build().toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(params, headers), ErrorCode.class);

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockPremium().getAccountId(), AccountType.NORMAL.getName()));

        assertTrue(result.getStatusCode().is4xxClientError());
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

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/premium/verify")
                .build().toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(params, headers), ErrorCode.class);

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockPremium().getAccountId(), AccountType.NORMAL.getName()));

        assertTrue(result.getStatusCode().is4xxClientError());
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

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/premium/verify")
                .build().toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(params, headers), ErrorCode.class);

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockPremium().getAccountId(), AccountType.NORMAL.getName()));

        assertTrue(result.getStatusCode().is4xxClientError());
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

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/premium/verify")
                .build().toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(params, headers), ErrorCode.class);

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockPremium().getAccountId(), AccountType.NORMAL.getName()));

        assertTrue(result.getStatusCode().is4xxClientError());
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

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/premium/verify")
                .build().toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(params, headers), ErrorCode.class);

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockPremium().getAccountId(), AccountType.NORMAL.getName()));

        assertTrue(result.getStatusCode().is4xxClientError());
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

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/premium/verify")
                .build().toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(params, headers), ErrorCode.class);

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockPremium().getAccountId(), AccountType.NORMAL.getName()));

        assertTrue(result.getStatusCode().is4xxClientError());
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

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/premium/verify")
                .build().toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(params, headers), ErrorCode.class);

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockPremium().getAccountId(), AccountType.NORMAL.getName()));

        assertTrue(result.getStatusCode().is4xxClientError());
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

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/premium/verify")
                .build().toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(params, headers), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
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
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/premium/pay")
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockPremium().getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testPremium_PayForPremium_ContextAccountIdIsNull()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/premium/pay")
                .queryParam("method", "CARD")
                .build().toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
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
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/premium/renew")
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockPremium().getAccountId(), AccountType.PREMIUM.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testPremium_RenewPremium_ContextAccountIdIsNull()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/premium/renew")
                .queryParam("method", "CARD")
                .build().toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
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
    void testPremium_CancelPremium_ContextAccountIdIsNull()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/premium/cancel")
                .build().toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
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
        final var params = PremiumCompanyParam.builder()
                .withNip("12345678901")
                .withBankAccountNumber("42424242424242424242424242")
                .build();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/premium/updateCompanyDetails")
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockPremium().getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(params, headers), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testPremium_UpdatePremiumCompanyDetails_WithoutRequiredNipField()
    {
        final var params = PremiumCompanyParam.builder()
                .withCompanyName("JanBud")
                .withBankAccountNumber("42424242424242424242424242")
                .build();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/premium/updateCompanyDetails")
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockPremium().getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(params, headers), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testPremium_UpdatePremiumCompanyDetails_WithoutRequireBankAccountNumberField()
    {
        final var params = PremiumCompanyParam.builder()
                .withCompanyName("JanBud")
                .withNip("12345678901")
                .build();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/premium/updateCompanyDetails")
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockPremium().getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(params, headers), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testPremium_UpdatePremiumCompanyDetails_ContextAccountIdIsNull()
    {
        final var params = PremiumCompanyParam.builder()
                .withCompanyName("JanBud")
                .withNip("12345678901")
                .withBankAccountNumber("42424242424242424242424242")
                .build();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/premium/updateCompanyDetails")
                .build().toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(params, headers), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
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
