package com.turi.authentication.infrastructure.adapter.application.queries.registration;

import com.turi.account.domain.model.AccountType;
import lombok.*;

@Getter
@ToString
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
