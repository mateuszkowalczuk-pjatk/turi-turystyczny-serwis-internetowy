package com.turi.premium.domain.port;

import com.turi.premium.domain.model.Premium;

import java.time.LocalDate;
import java.util.List;

public interface PremiumRepository
{
    List<Premium> findAll();

    List<Premium> findAllByExpiresDateBeforeCurrentDateAndStatusIsActive(final LocalDate currentDate, final int status);

    Premium findById(final Long id);

    Premium findByAccount(final Long accountId);

    Premium findByLoginToken(final String loginToken);

    Long insert(final Premium premium);

    void update(final Long id, final Premium premium);
}
