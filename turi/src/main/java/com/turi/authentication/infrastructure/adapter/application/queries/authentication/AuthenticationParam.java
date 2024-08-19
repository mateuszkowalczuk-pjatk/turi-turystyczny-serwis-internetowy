package com.turi.authentication.infrastructure.adapter.application.queries.authentication;

import lombok.*;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public final class AuthenticationParam
{
    private String login;
    private String password;
}
