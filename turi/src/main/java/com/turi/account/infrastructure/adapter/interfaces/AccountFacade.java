package com.turi.account.infrastructure.adapter.interfaces;

import com.turi.account.domain.model.Account;
import com.turi.account.domain.port.AccountService;
import com.turi.infrastructure.common.ObjectId;
import com.turi.infrastructure.exception.BadRequestParameterException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AccountFacade
{
    private final AccountService service;

    public Boolean isAddressExists(final String country,
                                   final String city,
                                   final String zipCode,
                                   final String street,
                                   final String buildingNumber,
                                   final Integer apartmentNumber)
    {
        return AccountResponse.of(service.isAddressExists(country,
                city,
                zipCode,
                street,
                buildingNumber,
                apartmentNumber));
    }

    public Account getByLogin(final String login)
    {
        return service.getByLogin(login);
    }

    public Boolean isLoginExists(final String login)
    {
        return AccountResponse.of(service.isLoginExists(login));
    }

    public Account getByEmail(final String email)
    {
        return service.getByEmail(email);
    }

    public Boolean isEmailExists(final String email)
    {
        return AccountResponse.of(service.isEmailExists(email));
    }

    public Boolean isPhoneNumberExists(final String phoneNumber)
    {
        return AccountResponse.of(service.isPhoneNumberExists(phoneNumber));
    }

    public Account createAccount(final Account account)
    {
        passwordValidation(account.getPassword());

        return AccountResponse.of(service.createAccount(account));
    }

    public Account updateOwnerDetails(final String accountId, final Account account)
    {
        final var id = ObjectId.of(accountId).getValue();

        return AccountResponse.of(service.updateOwnerDetails(id, account));
    }

    public Account resetPassword(final String accountId, final String password)
    {
        passwordValidation(password);

        final var id = ObjectId.of(accountId).getValue();

        return AccountResponse.of(service.resetPassword(id, password));
    }

    private void passwordValidation(final String password)
    {
        final var validator = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*?])[A-Za-z\\d!@#$%^&*?]{8,}$";

        if (password == null || !password.matches(validator))
        {
            throw new BadRequestParameterException("Invalid account password. Password must be at least 8 characters long, containing at least one lowercase letter, one uppercase letter, one digit, and one special character (!@#$%^&*?).");
        }
    }
}
