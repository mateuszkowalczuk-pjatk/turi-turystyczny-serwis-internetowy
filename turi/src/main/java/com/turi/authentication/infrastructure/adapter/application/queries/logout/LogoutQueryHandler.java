package com.turi.authentication.infrastructure.adapter.application.queries.logout;

import com.turi.authentication.domain.port.AuthenticationService;
import com.turi.infrastructure.crud.queries.QueryHandler;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LogoutQueryHandler implements QueryHandler<LogoutQuery, HttpServletResponse>
{
    private final AuthenticationService service;

    @Override
    public HttpServletResponse execute(final LogoutQuery query)
    {
        return service.logout(query.logout());
    }
}
