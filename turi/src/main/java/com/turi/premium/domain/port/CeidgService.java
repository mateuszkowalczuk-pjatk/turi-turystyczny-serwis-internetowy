package com.turi.premium.domain.port;

import com.turi.premium.domain.model.PremiumCompanyParam;

public interface CeidgService
{
    boolean verifyCompany(final PremiumCompanyParam params);
}
