package com.turi.premium.domain.port;

import com.turi.payment.domain.model.PaymentMethod;
import com.turi.premium.domain.model.*;

public interface PremiumService
{
    PremiumOffer getOffer();

    Premium getById(final Long id);

    Premium getByAccount(final Long accountId);

    Long getAccountId(final Long premiumId);

    Premium getByLoginToken(final String loginToken);

    Boolean isExistsForAccount(final Long accountId);

    Premium checkPayment(final Long accountId);

    PremiumLogin sendLoginCode(final Long accountId, final String email);

    Premium verify(final Long accountId, final PremiumVerifyParam params);

    Long login(final String loginToken, final Integer code);

    String pay(final Long accountId, final PaymentMethod method);

    String renew(final Long accountId, final PaymentMethod method);

    Premium cancel(final Long accountId);

    Premium updateCompanyDetails(final Long accountId, final PremiumCompanyParam params);

    void updateAllPremiumStatusAndAccountTypeIfPremiumExpired();

    void deleteAllExpiredLoginDetails();
}
