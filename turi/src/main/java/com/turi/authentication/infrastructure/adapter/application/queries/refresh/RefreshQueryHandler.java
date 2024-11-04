package com.turi.authentication.infrastructure.adapter.application.queries.refresh;

import com.turi.authentication.domain.model.Authentication;
import com.turi.authentication.domain.port.AuthenticationService;
import com.turi.infrastructure.crud.queries.QueryHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RefreshQueryHandler implements QueryHandler<RefreshQuery, Authentication>
{
    private final AuthenticationService service;

    @Override
    public Authentication execute(final RefreshQuery query)
    {
        return service.refresh(query.refresh());
    }
}
