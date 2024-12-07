package com.turi.premium.infrastructure.adapter.interfaces;

import com.turi.address.domain.model.Address;
import com.turi.infrastructure.common.ContextHandler;
import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.payment.domain.model.PaymentMethod;
import com.turi.payment.domain.model.PaymentStripeResponse;
import com.turi.premium.domain.model.*;
import com.turi.premium.domain.port.PremiumService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PremiumFacade
{
    private final PremiumService service;

    public ResponseEntity<PremiumOffer> getPremiumOffer()
    {
        return PremiumResponse.of(service.getOffer());
    }

    public ResponseEntity<Premium> getPremiumByAccount()
    {
        final var accountId = ContextHandler.getIdFromContext();

        return PremiumResponse.of(service.getByAccount(accountId));
    }

    public ResponseEntity<Boolean> isPremiumExistsForAccount()
    {
        final var accountId = ContextHandler.getIdFromContext();

        return PremiumResponse.of(service.isExistsForAccount(accountId));
    }

    public ResponseEntity<Premium> checkPaymentForPremium()
    {
        final var accountId = ContextHandler.getIdFromContext();

        return PremiumResponse.of(service.checkPayment(accountId));
    }

    public PremiumLogin sendPremiumLoginCode(final Long accountId, final String email)
    {
        if (accountId == null || email == null)
        {
            throw new BadRequestParameterException("Parameters accountId and email are required.");
        }

        return service.sendLoginCode(accountId, email);
    }

    public ResponseEntity<Premium> verifyPremium(final PremiumVerifyParam params)
    {
        if (params == null || params.getFirstName() == null || params.getLastName() == null || addressDetailsCheck(params.getAddress()) || params.getBankAccountNumber() == null || params.getCompanyName() == null || params.getNip() == null)
        {
            throw new BadRequestParameterException("Parameters firstName, lastName, companyName, address (with fields country, city, zipCode, street and buildingNumber), nip and bankAccountNumber are required.");
        }

        final var accountId = ContextHandler.getIdFromContext();

        bankAccountNumberValidation(params.getBankAccountNumber());

        nipValidation(params.getNip());

        return PremiumResponse.of(service.verify(accountId, params));
    }

    public Long loginIntoPremiumAccount(final String loginToken, final Integer code)
    {
        if (loginToken == null || code == null)
        {
            throw new BadRequestParameterException("Parameters loginToken and code must not be null.");
        }

        return service.login(loginToken, code);
    }

    public ResponseEntity<PaymentStripeResponse> payForPremium(final PaymentMethod method)
    {
        if (method == null)
        {
            throw new BadRequestParameterException("Parameter method is required.");
        }

        final var accountId = ContextHandler.getIdFromContext();

        return PremiumResponse.of(service.pay(accountId, method));
    }

    public ResponseEntity<PaymentStripeResponse> renewPremium(final PaymentMethod method)
    {
        if (method == null)
        {
            throw new BadRequestParameterException("Parameter method is required.");
        }

        final var accountId = ContextHandler.getIdFromContext();

        return PremiumResponse.of(service.renew(accountId, method));
    }

    public ResponseEntity<Premium> cancelPremium()
    {
        final var accountId = ContextHandler.getIdFromContext();

        return PremiumResponse.of(service.cancel(accountId));
    }

    public ResponseEntity<Premium> updatePremiumCompanyDetails(final PremiumCompanyParam params)
    {
        if (params == null || addressDetailsCheck(params.getAddress()) || params.getBankAccountNumber() == null || params.getCompanyName() == null || params.getNip() == null)
        {
            throw new BadRequestParameterException("Parameters companyName, address (with fields country, city, zipCode, street and buildingNumber), nip and bankAccountNumber are required.");
        }

        final var accountId = ContextHandler.getIdFromContext();

        bankAccountNumberValidation(params.getBankAccountNumber());

        nipValidation(params.getNip());

        return PremiumResponse.of(service.updateCompanyDetails(accountId, params));
    }

    private boolean addressDetailsCheck(final Address address)
    {
        return address == null || address.getCountry() == null || address.getCity() == null || address.getZipCode() == null || address.getStreet() == null || address.getBuildingNumber() == null;
    }

    private void bankAccountNumberValidation(final String bankAccountNumber)
    {
        final var validator = "^\\d{26}$";

        if (bankAccountNumber == null || !bankAccountNumber.matches(validator))
        {
            throw new BadRequestParameterException("Invalid bank account number. Bank account number must contain exactly 26 digits.");
        }
    }

    private void nipValidation(final String nip)
    {
        final var validator = "^\\d{10}$";

        if (nip == null || !nip.matches(validator))
        {
            throw new BadRequestParameterException("Invalid NIP. NIP must contain exactly 10 digits.");
        }
    }
}
