package com.turi.premium.domain.port;

import com.turi.payment.domain.model.PaymentMethod;
import com.turi.payment.domain.model.PaymentStripeResponse;
import com.turi.premium.domain.model.Premium;
import com.turi.premium.domain.model.PremiumCompanyParam;
import com.turi.premium.domain.model.PremiumOffer;
import com.turi.premium.domain.model.PremiumVerifyParam;

public interface PremiumService
{
    PremiumOffer getOffer();

    Premium getById(final Long id);

    Premium getByAccount(final Long accountId);

    Boolean isExistsForAccount(final Long accountId);

    Premium checkPayment(final Long accountId);

    Premium verify(final Long accountId, final PremiumVerifyParam params);

    PaymentStripeResponse pay(final Long accountId, final PaymentMethod method);

    PaymentStripeResponse renew(final Long accountId, final PaymentMethod method);

    Premium cancel(final Long accountId);

    Premium updateCompanyDetails(final Long accountId, final PremiumCompanyParam params);

    void updateAllPremiumStatusAndAccountTypeIfPremiumExpired();
}
