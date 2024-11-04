package com.turi.authentication.domain.port;

import com.turi.authentication.domain.model.Authentication;
import com.turi.authentication.infrastructure.adapter.application.queries.authentication.AuthenticationParam;
import com.turi.authentication.infrastructure.adapter.application.queries.logout.LogoutParam;
import com.turi.authentication.infrastructure.adapter.application.queries.refresh.RefreshParam;
import com.turi.authentication.infrastructure.adapter.application.queries.registration.RegistrationParam;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService
{
    Authentication register(final RegistrationParam params);

    Authentication authenticate(final AuthenticationParam params);

    Authentication refresh(final RefreshParam params);

    HttpServletResponse logout(final LogoutParam params);
}
