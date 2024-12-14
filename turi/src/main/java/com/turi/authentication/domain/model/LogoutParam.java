package com.turi.authentication.domain.model;

import jakarta.servlet.http.HttpServletResponse;
import lombok.*;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@Builder(setterPrefix = "with")
public final class LogoutParam
{
    private String refreshToken;
    private HttpServletResponse response;
}
