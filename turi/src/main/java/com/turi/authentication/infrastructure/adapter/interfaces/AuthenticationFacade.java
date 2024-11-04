package com.turi.authentication.infrastructure.adapter.interfaces;

import com.turi.authentication.infrastructure.adapter.application.queries.authentication.AuthenticationParam;
import com.turi.authentication.infrastructure.adapter.application.queries.authentication.AuthenticationQuery;
import com.turi.authentication.infrastructure.adapter.application.queries.authentication.AuthenticationQueryHandler;
import com.turi.authentication.infrastructure.adapter.application.queries.logout.LogoutParam;
import com.turi.authentication.infrastructure.adapter.application.queries.logout.LogoutQuery;
import com.turi.authentication.infrastructure.adapter.application.queries.logout.LogoutQueryHandler;
import com.turi.authentication.infrastructure.adapter.application.queries.refresh.RefreshParam;
import com.turi.authentication.infrastructure.adapter.application.queries.refresh.RefreshQuery;
import com.turi.authentication.infrastructure.adapter.application.queries.refresh.RefreshQueryHandler;
import com.turi.authentication.infrastructure.adapter.application.queries.registration.RegistrationParam;
import com.turi.authentication.infrastructure.adapter.application.queries.registration.RegistrationQuery;
import com.turi.authentication.infrastructure.adapter.application.queries.registration.RegistrationQueryHandler;
import com.turi.infrastructure.crud.QueryBusImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthenticationFacade
{
    private final RegistrationQueryHandler registrationQueryHandler;
    private final AuthenticationQueryHandler authenticationQueryHandler;
    private final RefreshQueryHandler refreshQueryHandler;
    private final LogoutQueryHandler logoutQueryHandler;

    public ResponseEntity<?> register(final RegistrationParam params)
    {
        final var query = new RegistrationQuery(params);
        final var queryBus = new QueryBusImpl<>(registrationQueryHandler);
        return AuthenticationResponse.of(queryBus.process(query));
    }

    public ResponseEntity<?> authenticate(final AuthenticationParam params)
    {
        final var query = new AuthenticationQuery(params);
        final var queryBus = new QueryBusImpl<>(authenticationQueryHandler);
        return AuthenticationResponse.of(queryBus.process(query));
    }

    public ResponseEntity<?> refresh(final RefreshParam params)
    {
        final var query = new RefreshQuery(params);
        final var queryBus = new QueryBusImpl<>(refreshQueryHandler);
        return AuthenticationResponse.of(queryBus.process(query));
    }

    public ResponseEntity<?> logout(final LogoutParam params)
    {
        final var query = new LogoutQuery(params);
        final var queryBus = new QueryBusImpl<>(logoutQueryHandler);
        return AuthenticationResponse.of(queryBus.process(query));
    }
}
