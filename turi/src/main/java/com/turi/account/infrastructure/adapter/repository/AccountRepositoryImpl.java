package com.turi.account.infrastructure.adapter.repository;

import com.turi.account.domain.exception.AccountNotFoundException;
import com.turi.account.domain.model.Account;
import com.turi.account.domain.port.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class AccountRepositoryImpl implements AccountRepository
{
    private final AccountRepositoryDao accountRepositoryDao;

    @Override
    public List<Account> findAll()
    {
        return accountRepositoryDao.findAll().stream()
                .map(Account::of)
                .collect(Collectors.toList());
    }

    @Override
    public Account findById(final Long id)
    {
        return accountRepositoryDao.findById(id)
                .map(Account::of)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }

    @Override
    public Account findByUserId(final Long userId)
    {
        return accountRepositoryDao.findByUserId(userId)
                .map(Account::of)
                .orElse(null);
    }

    @Override
    public Account findByAddressId(final Long addressId)
    {
        return accountRepositoryDao.findByAddressId(addressId)
                .map(Account::of)
                .orElse(null);
    }

    @Override
    public Account findByPhoneNumber(final String phoneNumber)
    {
        return accountRepositoryDao.findByPhoneNumber(phoneNumber)
                .map(Account::of)
                .orElse(null);
    }

    @Override
    public Long insert(final Account account)
    {
        final var entity = AccountEntity.of(account);

        return accountRepositoryDao.saveAndFlush(entity).getAccountId();
    }

    @Override
    public void update(final Long accountId, final Account account)
    {
        final var accountEntity = accountRepositoryDao.findById(accountId).orElse(null);

        final var entity = AccountEntity.of(account);

        Optional.ofNullable(accountEntity).ifPresent(e -> {
            e.setUserId(entity.getUserId());
            e.setAddressId(entity.getAddressId());
            e.setAccountType(entity.getAccountType());
            e.setFirstName(entity.getFirstName());
            e.setLastName(entity.getLastName());
            e.setBirthDate(entity.getBirthDate());
            e.setPhoneNumber(entity.getPhoneNumber());
            e.setGender(entity.getGender());

            accountRepositoryDao.saveAndFlush(accountEntity);
        });
    }

    @Override
    public void delete(final Long accountId)
    {
        final var account = findById(accountId);

        accountRepositoryDao.deleteById(account.getAccountId());
    }
}
