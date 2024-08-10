package com.turi.authentication.domain.port;

import com.turi.account.domain.model.Account;
import com.turi.authentication.domain.model.Authentication;
import com.turi.authentication.infrastructure.adapter.application.queries.authentication.AuthenticationParam;
import com.turi.authentication.infrastructure.adapter.application.queries.registration.RegistrationParam;

public interface AuthenticationService
{
    Account register(final RegistrationParam params);

    Authentication authenticate(final AuthenticationParam params);
}
