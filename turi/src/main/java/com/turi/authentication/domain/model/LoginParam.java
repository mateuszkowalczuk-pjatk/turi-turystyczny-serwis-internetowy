package com.turi.authentication.domain.model;

import lombok.*;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Builder(setterPrefix = "with")
public final class LoginParam
{
    private String login;
    private String password;
}
