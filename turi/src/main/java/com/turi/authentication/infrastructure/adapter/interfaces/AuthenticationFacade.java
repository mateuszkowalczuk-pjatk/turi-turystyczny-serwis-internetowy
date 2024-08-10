package com.turi.authentication.infrastructure.adapter.interfaces;

import com.turi.account.domain.model.Account;
import com.turi.account.infrastructure.adapter.interfaces.AccountResponse;
import com.turi.authentication.domain.model.Authentication;
import com.turi.authentication.infrastructure.adapter.application.queries.authentication.AuthenticationParam;
import com.turi.authentication.infrastructure.adapter.application.queries.authentication.AuthenticationQuery;
import com.turi.authentication.infrastructure.adapter.application.queries.authentication.AuthenticationQueryHandler;
import com.turi.authentication.infrastructure.adapter.application.queries.registration.RegistrationParam;
import com.turi.authentication.infrastructure.adapter.application.queries.registration.RegistrationQuery;
import com.turi.authentication.infrastructure.adapter.application.queries.registration.RegistrationQueryHandler;
import com.turi.infrastructure.crud.QueryBusImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthenticationFacade
{
    private final RegistrationQueryHandler registrationQueryHandler;
    private final AuthenticationQueryHandler authenticationQueryHandler;

    public Account register(final RegistrationParam params)
    {
        final var query = new RegistrationQuery(params);
        final var queryBus = new QueryBusImpl<>(registrationQueryHandler);
        return AccountResponse.of(queryBus.process(query));
    }

    public Authentication authenticate(final AuthenticationParam params)
    {
        final var query = new AuthenticationQuery(params);
        final var queryBus = new QueryBusImpl<>(authenticationQueryHandler);
        return AuthenticationResponse.of(queryBus.process(query));
    }
}
