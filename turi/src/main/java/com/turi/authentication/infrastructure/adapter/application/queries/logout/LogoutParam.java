package com.turi.authentication.infrastructure.adapter.application.queries.logout;

import jakarta.servlet.http.HttpServletResponse;
import lombok.*;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public final class LogoutParam
{
    private String refreshToken;
    private HttpServletResponse response;
}
