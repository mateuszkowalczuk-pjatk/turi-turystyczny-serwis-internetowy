package com.turi.premium.infrastructure.adapter.repository;

import com.turi.premium.domain.exception.InvalidPremiumException;
import com.turi.premium.domain.exception.PremiumNotFoundException;
import com.turi.premium.domain.model.Premium;
import com.turi.premium.domain.model.PremiumStatus;
import com.turi.premium.domain.port.PremiumRepository;
import com.turi.testhelper.annotation.RepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RepositoryTest
class PremiumRepositoryTest
{
    @Autowired
    private PremiumRepository repository;

    @Test
    void testPremium_FindAll()
    {
        final var premium = mockPremium();

        final var result = repository.findAll();

        assertNotNull(result);
        assertThat(result).isEqualTo(List.of(premium));
    }

    @Test
    void testPremium_FindAllByExpiresDateBeforeCurrentDateAndStatusIsActive()
    {
        final var result = repository.findAllByExpiresDateBeforeCurrentDateAndStatusIsActive(LocalDate.now().plusYears(1), 1);

        assertNotNull(result);
        assertThat(result).isEqualTo(List.of(mockPremium()));
    }

    @Test
    void testPremium_FindAllByExpiresDateBeforeCurrentDateAndStatusIsActive_NothingFound()
    {
        final var result = repository.findAllByExpiresDateBeforeCurrentDateAndStatusIsActive(LocalDate.now(), 1);

        assertThat(result).isEmpty();
    }

    @Test
    void testPremium_FindById()
    {
        final var premium = mockPremium();

        final var result = repository.findById(premium.getPremiumId());

        assertNotNull(result);
        assertThat(result).isEqualTo(premium);
    }

    @Test
    void testPremium_FindById_NotFound()
    {
        final var premium = mockNewPremium();

        assertThrows(PremiumNotFoundException.class, () -> repository.findById(premium.getPremiumId()));
    }

    @Test
    void testPremium_FindByAccount()
    {
        final var premium = mockPremium();

        final var result = repository.findByAccount(premium.getAccountid());

        assertNotNull(result);
        assertThat(result).isEqualTo(premium);
    }

    @Test
    void testPremium_FindByAccount_NotFound()
    {
        assertNull(repository.findByAccount(mockNewPremium().getAccountid()));
    }

    @Test
    void testPremium_FindByLoginToken()
    {
        final var premium = mockPremium();

        final var result = repository.findByLoginToken(premium.getLoginToken());

        assertNotNull(result);
        assertThat(result).isEqualTo(premium);
    }

    @Test
    void testPremium_FindByLoginToken_NotFound()
    {
        assertNull(repository.findByLoginToken(mockNewPremium().getLoginToken()));
    }

    @Test
    void testPremium_Insert()
    {
        final var premium = mockNewPremium();

        final var premiumId = repository.insert(premium);

        final var result = repository.findById(premiumId);

        assertNotNull(result);
        assertThat(result).isEqualTo(premium);
    }

    @Test
    void testPremium_Insert_WithoutRequiredAccountIdField()
    {
        final var premium = mockNewPremium();
        premium.setAccountid(null);

        assertThrows(InvalidPremiumException.class, () -> repository.insert(premium));
    }

    @Test
    void testPremium_Insert_WithoutRequiredCompanyNameField()
    {
        final var premium = mockNewPremium();
        premium.setCompanyName(null);

        assertThrows(InvalidPremiumException.class, () -> repository.insert(premium));
    }

    @Test
    void testPremium_Insert_WithoutRequiredNipField()
    {
        final var premium = mockNewPremium();
        premium.setNip(null);

        assertThrows(InvalidPremiumException.class, () -> repository.insert(premium));
    }

    @Test
    void testPremium_Insert_WithoutRequiredBankAccountNumberField()
    {
        final var premium = mockNewPremium();
        premium.setBankAccountNumber(null);

        assertThrows(InvalidPremiumException.class, () -> repository.insert(premium));
    }

    @Test
    void testPremium_Insert_WithoutRequiredStatusField()
    {
        final var premium = mockNewPremium();
        premium.setStatus(null);

        assertThrows(InvalidPremiumException.class, () -> repository.insert(premium));
    }

    @Test
    void testPremium_Update()
    {
        final var premium = mockPremium();

        premium.setExpiresDate(LocalDate.now());

        repository.update(premium.getPremiumId(), premium);

        final var result = repository.findById(premium.getPremiumId());

        assertNotNull(result);
        assertThat(result).isEqualTo(premium);
    }

    @Test
    void testPremium_Update_PremiumNotFound()
    {
        final var premium = mockPremium();

        premium.setPremiumId(mockNewPremium().getPremiumId());

        repository.update(premium.getPremiumId(), premium);

        assertThrows(PremiumNotFoundException.class, () -> repository.findById(premium.getPremiumId()));
    }

    @Test
    void testPayment_Update_WithoutRequiredAccountIdField()
    {
        final var premium = mockPremium();

        premium.setAccountid(null);

        assertThrows(InvalidPremiumException.class, () -> repository.update(premium.getPremiumId(), premium));
    }

    @Test
    void testPremium_Update_WithoutRequiredCompanyNameField()
    {
        final var premium = mockPremium();

        premium.setCompanyName(null);

        assertThrows(InvalidPremiumException.class, () -> repository.update(premium.getPremiumId(), premium));
    }

    @Test
    void testPremium_Update_WithoutRequiredNipField()
    {
        final var premium = mockPremium();

        premium.setNip(null);

        assertThrows(InvalidPremiumException.class, () -> repository.update(premium.getPremiumId(), premium));
    }

    @Test
    void testPremium_Update_WithoutRequiredBankAccountNumberField()
    {
        final var premium = mockPremium();

        premium.setBankAccountNumber(null);

        assertThrows(InvalidPremiumException.class, () -> repository.update(premium.getPremiumId(), premium));
    }

    @Test
    void testPremium_Update_WithoutRequiredStatusField()
    {
        final var premium = mockPremium();

        premium.setStatus(null);

        assertThrows(InvalidPremiumException.class, () -> repository.update(premium.getPremiumId(), premium));
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
