package com.turi.authentication.infrastructure.adapter.application.queries.registration;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public final class RegistrationParam
{
    private String username;
    private String email;
    private String password;
}
