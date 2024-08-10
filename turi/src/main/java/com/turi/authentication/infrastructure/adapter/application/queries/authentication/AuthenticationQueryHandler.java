package com.turi.authentication.infrastructure.adapter.application.queries.authentication;

import com.turi.authentication.domain.model.Authentication;
import com.turi.authentication.domain.port.AuthenticationService;
import com.turi.infrastructure.crud.queries.QueryHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthenticationQueryHandler implements QueryHandler<AuthenticationQuery, Authentication>
{
    private final AuthenticationService service;

    @Override
    public Authentication execute(final AuthenticationQuery query)
    {
        return service.authenticate(query.authenticate());
    }
}
