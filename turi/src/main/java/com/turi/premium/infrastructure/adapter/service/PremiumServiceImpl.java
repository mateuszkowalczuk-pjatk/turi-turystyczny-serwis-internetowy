package com.turi.premium.infrastructure.adapter.service;

import com.turi.account.domain.model.Account;
import com.turi.account.infrastructure.adapter.interfaces.AccountFacade;
import com.turi.address.domain.model.Address;
import com.turi.address.infrastructure.adapter.interfaces.AddressFacade;
import com.turi.infrastructure.common.CodeGenerator;
import com.turi.infrastructure.common.EmailSender;
import com.turi.infrastructure.common.HashToken;
import com.turi.infrastructure.config.PremiumOfferProperties;
import com.turi.infrastructure.config.SecurityProperties;
import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.payment.domain.model.PaymentMethod;
import com.turi.payment.domain.model.PaymentStripeResponse;
import com.turi.payment.infrastructure.adapter.interfaces.PaymentFacade;
import com.turi.premium.domain.exception.*;
import com.turi.premium.domain.model.*;
import com.turi.premium.domain.port.CeidgService;
import com.turi.premium.domain.port.PremiumRepository;
import com.turi.premium.domain.port.PremiumService;
import com.turi.user.domain.exception.UserResetCodeRecentlySentException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PremiumServiceImpl implements PremiumService
{
    private final EmailSender emailSender;
    private final CeidgService ceidgService;
    private final PaymentFacade paymentFacade;
    private final AccountFacade accountFacade;
    private final AddressFacade addressFacade;
    private final PremiumRepository repository;
    private final SecurityProperties securityProperties;
    private final PremiumOfferProperties premiumOfferProperties;

    @Override
    public PremiumOffer getOffer()
    {
        return PremiumOffer.builder()
                .withPrice(premiumOfferProperties.getPrice())
                .withLength(premiumOfferProperties.getLength())
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
    public Premium getByLoginToken(final String loginToken)
    {
        return repository.findByLoginToken(loginToken);
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
                .withBuyDate(premium.getStatus().equals(PremiumStatus.ACTIVE) ? premium.getBuyDate() : buyDate)
                .withExpiresDate(premium.getStatus().equals(PremiumStatus.ACTIVE) ? premium.getExpiresDate().plusMonths(premiumOfferProperties.getLength()) : buyDate.plusMonths(premiumOfferProperties.getLength()))
                .withStatus(PremiumStatus.ACTIVE)
                .build();

        repository.update(premium.getPremiumId(), premiumToUpdate);

        accountFacade.updateAccountTypeToPremium();

        return getById(premium.getPremiumId());
    }

    @Override
    public PremiumLogin sendLoginCode(final Long accountId, final String email)
    {
        final var premium = getByAccount(accountId);

        if (premium == null)
        {
            throw new PremiumNotFoundByAccountException(accountId);
        }

        if (premium.getLoginExpiresAt() == null || premium.getLoginExpiresAt().minusMinutes(10).isBefore(LocalDateTime.now()))
        {
            final var code = CodeGenerator.generateCode();

            final var token = securityProperties.getSecretKey() + "-" + premium.getAccountid() + "-" + UUID.randomUUID();

            premium.setLoginCode(code);
            premium.setLoginToken(HashToken.hash(token));
            premium.setLoginExpiresAt(LocalDateTime.now().plusSeconds(securityProperties.getAccessTokenExpirationTime()));

            repository.update(premium.getPremiumId(), premium);

            emailSender.sendEmail(email, "Premium account login code.", premium.getLoginCode());

            return PremiumLogin.builder()
                    .withLoginToken(premium.getLoginToken())
                    .withLoginTokenExpiresIn(securityProperties.getAccessTokenExpirationTime())
                    .build();
        }
        else
        {
            throw new UserResetCodeRecentlySentException();
        }
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

    @Override
    public Long login(final String loginToken, final Integer code)
    {
        final var premium = getByLoginToken(loginToken);

        if (premium == null)
        {
            throw new PremiumNotFoundByLoginTokenException(loginToken);
        }

        if (!premium.getLoginCode().equals(code))
        {
            throw new BadRequestParameterException("Invalid premium login code.");
        }

        if (premium.getLoginExpiresAt().isAfter(LocalDateTime.now()))
        {
            deletePasswordLoginDetails(premium);

            return premium.getAccountid();
        }
        else
        {
            deletePasswordLoginDetails(premium);

            throw new PremiumLoginCodeExpiredException();
        }
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

        if (premium.getStatus().equals(PremiumStatus.ACTIVE))
        {
            throw new PremiumActivatedException(premium.getPremiumId());
        }

        return paymentFacade.payForPremium(accountId, premiumOfferProperties.getPrice(), method);
    }

    @Override
    public PaymentStripeResponse renew(final Long accountId, final PaymentMethod method)
    {
        final var premium = getByAccount(accountId);

        if (!premium.getStatus().equals(PremiumStatus.EXPIRED) && premium.getExpiresDate().isAfter(LocalDate.now().plusWeeks(1)))
        {
            throw new PremiumActivatedException(premium.getPremiumId());
        }

        return paymentFacade.payForPremium(premium.getPremiumId(), premiumOfferProperties.getPrice(), method);
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

    @Override
    public void deleteAllExpiredLoginDetails()
    {
        repository.findAll().stream()
                .filter(premium -> premium.getLoginExpiresAt() != null && premium.getLoginExpiresAt().isBefore(LocalDateTime.now()))
                .forEach(this::deletePasswordLoginDetails);
    }

    private void deletePasswordLoginDetails(final Premium premium)
    {
        premium.setLoginCode(null);
        premium.setLoginToken(null);
        premium.setLoginExpiresAt(null);

        repository.update(premium.getPremiumId(), premium);
    }
}
