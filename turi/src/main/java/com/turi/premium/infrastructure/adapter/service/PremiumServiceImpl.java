package com.turi.premium.infrastructure.adapter.service;

import com.turi.account.domain.model.Account;
import com.turi.account.infrastructure.adapter.interfaces.AccountFacade;
import com.turi.address.domain.model.Address;
import com.turi.address.infrastructure.adapter.interfaces.AddressFacade;
import com.turi.infrastructure.config.PremiumOfferProperties;
import com.turi.payment.domain.model.PaymentMethod;
import com.turi.payment.domain.model.PaymentStripeResponse;
import com.turi.payment.infrastructure.adapter.interfaces.PaymentFacade;
import com.turi.premium.domain.exception.InvalidCompanyException;
import com.turi.premium.domain.exception.PremiumActivatedException;
import com.turi.premium.domain.exception.PremiumInactiveException;
import com.turi.premium.domain.exception.PremiumUnpaidException;
import com.turi.premium.domain.model.*;
import com.turi.premium.domain.port.CeidgService;
import com.turi.premium.domain.port.PremiumRepository;
import com.turi.premium.domain.port.PremiumService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PremiumServiceImpl implements PremiumService
{
    private final CeidgService ceidgService;
    private final PaymentFacade paymentFacade;
    private final AccountFacade accountFacade;
    private final AddressFacade addressFacade;
    private final PremiumRepository repository;
    private final PremiumOfferProperties properties;

    @Override
    public PremiumOffer getOffer()
    {
        return PremiumOffer.builder()
                .withPrice(properties.getPrice())
                .withLength(properties.getLength())
                .build();
    }

    @Override
    public Premium getById(final Long id)
    {
        return repository.findById(id);
    }

    @Override
    public Premium getByAccount(final Long accountId)
    {
        return repository.findByAccount(accountId);
    }

    @Override
    public Boolean isExistsForAccount(final Long accountId)
    {
        return getByAccount(accountId) != null;
    }

    @Override
    public Premium checkPayment(final Long accountId)
    {
        final var premium = getByAccount(accountId);

        if (!paymentFacade.isPaymentForPremiumSucceeded(premium.getPremiumId()))
        {
            throw new PremiumUnpaidException(accountId);
        }

        final var buyDate = LocalDate.now();

        final var premiumToUpdate = Premium.builder()
                .withAccountid(premium.getAccountid())
                .withCompanyName(premium.getCompanyName())
                .withNip(premium.getNip())
                .withBankAccountNumber(premium.getBankAccountNumber())
                .withBuyDate(buyDate)
                .withExpiresDate(buyDate.plusMonths(properties.getLength()))
                .withStatus(PremiumStatus.ACTIVE)
                .build();

        repository.update(premium.getPremiumId(), premiumToUpdate);

        accountFacade.updateAccountTypeToPremium();

        return getById(premium.getPremiumId());
    }

    @Override
    public Premium verify(final Long accountId, final PremiumVerifyParam params)
    {
        final var companyParams = PremiumCompanyParam.builder()
                .withAddress(params.getAddress())
                .withCompanyName(params.getCompanyName())
                .withNip(params.getNip())
                .build();

        if (!ceidgService.verifyCompany(companyParams))
        {
            throw new InvalidCompanyException();
        }

        updateVerifiedAccount(params.getFirstName(), params.getLastName(), params.getAddress());

        return createVerifiedPremium(accountId, params.getCompanyName(), params.getNip(), params.getBankAccountNumber());
    }

    private Premium createVerifiedPremium(final Long accountId, final String companyName, final String nip, final String bankAccountNumber)
    {
        final var premium = Premium.builder()
                .withAccountid(accountId)
                .withCompanyName(companyName)
                .withNip(nip)
                .withBankAccountNumber(bankAccountNumber)
                .withBuyDate(null)
                .withExpiresDate(null)
                .withStatus(PremiumStatus.UNPAID)
                .build();

        final var premiumId = repository.insert(premium);

        return getById(premiumId);
    }

    @Override
    public PaymentStripeResponse pay(final Long accountId, final PaymentMethod method)
    {
        final var premium = getByAccount(accountId);

        if (!premium.getStatus().equals(PremiumStatus.UNPAID))
        {
            throw new PremiumActivatedException(premium.getPremiumId());
        }

        return paymentFacade.payForPremium(accountId, properties.getPrice(), method);
    }

    @Override
    public PaymentStripeResponse renew(final Long accountId, final PaymentMethod method)
    {
        final var premium = getByAccount(accountId);

        if (!premium.getStatus().equals(PremiumStatus.EXPIRED) || premium.getExpiresDate().isBefore(LocalDate.now().plusWeeks(1)))
        {
            throw new PremiumActivatedException(premium.getPremiumId());
        }

        return paymentFacade.payForPremium(premium.getPremiumId(), properties.getPrice(), method);
    }

    @Override
    public Premium cancel(final Long accountId)
    {
        final var premium = getByAccount(accountId);

        if (premium.getStatus() != PremiumStatus.ACTIVE)
        {
            throw new PremiumInactiveException(premium.getPremiumId());
        }

        premium.setExpiresDate(LocalDate.now());
        premium.setStatus(PremiumStatus.EXPIRED);

        repository.update(premium.getPremiumId(), premium);

        accountFacade.updateAccountTypeToNormal();

        return getById(accountId);
    }

    @Override
    public Premium updateCompanyDetails(final Long accountId, final PremiumCompanyParam params)
    {
        if (!ceidgService.verifyCompany(params))
        {
            throw new InvalidCompanyException();
        }

        updateVerifiedAccount(null, null, params.getAddress());

        return updateVerifiedPremium(accountId, params.getCompanyName(), params.getNip(), params.getBankAccountNumber());
    }

    private void updateVerifiedAccount(final String firstName, final String lastName, final Address address)
    {
        final var account = Objects.requireNonNull(accountFacade.getAccountById().getBody());

        final var addressId = Objects.requireNonNull(addressFacade.createAddress(address).getBody()).getAddressId();

        final var accountToUpdate = Account.builder()
                .withAddressId(addressId)
                .withAccountType(account.getAccountType())
                .withActivationCode(account.getActivationCode())
                .withActivationCodeExpiresAt(account.getActivationCodeExpiresAt())
                .withFirstName(firstName != null ? firstName : account.getFirstName())
                .withLastName(lastName != null ? lastName : account.getLastName())
                .withBirthDate(account.getBirthDate())
                .withPhoneNumber(account.getPhoneNumber())
                .withGender(account.getGender())
                .build();

        accountFacade.updateAccount(accountToUpdate);
    }

    private Premium updateVerifiedPremium(final Long accountId, final String companyName, final String nip, final String bankAccountNumber)
    {
        final var premium = getByAccount(accountId);

        final var premiumToUpdate = Premium.builder()
                .withAccountid(accountId)
                .withCompanyName(companyName)
                .withNip(nip)
                .withBankAccountNumber(bankAccountNumber)
                .withBuyDate(premium.getBuyDate())
                .withExpiresDate(premium.getExpiresDate())
                .withStatus(premium.getStatus())
                .build();

        repository.update(premium.getPremiumId(), premiumToUpdate);

        return getById(premium.getPremiumId());
    }

    @Override
    public void updateAllPremiumStatusAndAccountTypeIfPremiumExpired()
    {
        final var premiums = repository.findAllByExpiresDateBeforeCurrentDateAndStatusIsActive(LocalDate.now(), PremiumStatus.ACTIVE.getValue());

        premiums.forEach(premium -> {
            premium.setStatus(PremiumStatus.EXPIRED);

            repository.update(premium.getPremiumId(), premium);

            accountFacade.updateAccountTypeToNormal();
        });
    }
}
