package com.turi.authentication.infrastructure.adapter.application.queries.registration;

import com.turi.account.domain.model.Account;
import com.turi.authentication.domain.port.AuthenticationService;
import com.turi.infrastructure.crud.queries.QueryHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RegistrationQueryHandler implements QueryHandler<RegistrationQuery, Account>
{
    private final AuthenticationService service;

    @Override
    public Account execute(final RegistrationQuery query)
    {
        return service.register(query.register());
    }
}
