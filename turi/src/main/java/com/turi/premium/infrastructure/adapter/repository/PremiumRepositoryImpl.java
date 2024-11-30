package com.turi.premium.infrastructure.adapter.repository;

import com.turi.premium.domain.exception.PremiumNotFoundException;
import com.turi.premium.domain.model.Premium;
import com.turi.premium.domain.port.PremiumRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PremiumRepositoryImpl implements PremiumRepository
{
    private final PremiumRepositoryDao repositoryDao;

    @Override
    public List<Premium> findAllByExpiresDateBeforeCurrentDateAndStatusIsActive(final LocalDate currentDate, final int status)
    {
        return repositoryDao.findAllByExpiresDateBeforeAndStatus(currentDate, status).stream()
                .map(Premium::of)
                .toList();
    }

    @Override
    public Premium findById(final Long id)
    {
        return repositoryDao.findById(id)
                .map(Premium::of)
                .orElseThrow(() -> new PremiumNotFoundException(id));
    }

    @Override
    public Premium findByAccount(final Long accountId)
    {
        return repositoryDao.findByAccountid(accountId)
                .map(Premium::of)
                .orElse(null);
    }

    @Override
    public Long insert(final Premium premium)
    {
        final var entity = PremiumEntity.of(premium);

        return repositoryDao.saveAndFlush(entity).getPremiumId();
    }

    @Override
    public void update(final Long id, final Premium premium)
    {
        final var premiumEntity = repositoryDao.findById(id).orElse(null);

        final var entity = PremiumEntity.of(premium);

        Optional.ofNullable(premiumEntity).ifPresent(e -> {
            e.setCompanyName(entity.getCompanyName());
            e.setNip(entity.getNip());
            e.setBankAccountNumber(entity.getBankAccountNumber());
            e.setBuyDate(entity.getBuyDate());
            e.setExpiresDate(entity.getExpiresDate());
            e.setStatus(entity.getStatus());

            repositoryDao.saveAndFlush(premiumEntity);
        });
    }
}
