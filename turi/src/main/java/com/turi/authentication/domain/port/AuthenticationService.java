package com.turi.authentication.domain.port;

import com.turi.authentication.domain.model.*;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService
{
    Authentication register(final RegisterParam params);

    Authentication login(final LoginParam params);

    Authentication loginPremium(final String loginToken, final Integer code);

    Boolean authorize();

    Authentication refresh(final RefreshParam params);

    HttpServletResponse logout(final LogoutParam params);
}
