package com.turi.account.infrastructure.adapter.repository;

import com.turi.account.domain.exception.AccountNotFoundException;
import com.turi.account.domain.model.Account;
import com.turi.account.domain.port.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccountRepositoryImpl implements AccountRepository
{
    private final AccountRepositoryDao repositoryDao;

    @Override
    public Account findById(final Long id)
    {
        return repositoryDao.findById(id)
                .map(Account::of)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }

    @Override
    public Account findByUserId(final Long userId)
    {
        return repositoryDao.findByUserId(userId)
                .map(Account::of)
                .orElse(null);
    }

    @Override
    public Account findByAddressId(final Long addressId)
    {
        return repositoryDao.findByAddressId(addressId)
                .map(Account::of)
                .orElse(null);
    }

    @Override
    public Account findByPhoneNumber(final String phoneNumber)
    {
        return repositoryDao.findByPhoneNumber(phoneNumber)
                .map(Account::of)
                .orElse(null);
    }

    @Override
    public Long insert(final Account account)
    {
        final var entity = AccountEntity.of(account);

        return repositoryDao.saveAndFlush(entity).getAccountId();
    }

    @Override
    public void update(final Long id, final Account account)
    {
        final var accountEntity = repositoryDao.findById(id).orElse(null);

        final var entity = AccountEntity.of(account);

        Optional.ofNullable(accountEntity).ifPresent(e -> {
            e.setUserId(entity.getUserId());
            e.setAddressId(entity.getAddressId());
            e.setAccountType(entity.getAccountType());
            e.setActivateCode(entity.getActivateCode());
            e.setActivateCodeExpiresAt(entity.getActivateCodeExpiresAt());
            e.setFirstName(entity.getFirstName());
            e.setLastName(entity.getLastName());
            e.setBirthDate(entity.getBirthDate());
            e.setPhoneNumber(entity.getPhoneNumber());
            e.setGender(entity.getGender());

            repositoryDao.saveAndFlush(accountEntity);
        });
    }

    @Override
    public void delete(final Long id)
    {
        final var account = findById(id);

        repositoryDao.deleteById(account.getAccountId());
    }
}
