package com.turi.authentication.infrastructure.adapter.application.queries.authentication;

import com.turi.infrastructure.crud.Validator;
import com.turi.infrastructure.crud.queries.Query;
import com.turi.infrastructure.exception.BadRequestParameterException;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class AuthenticationQuery implements Query, Validator
{
    private final AuthenticationParam params;

    public AuthenticationParam authenticate()
    {
        return params;
    }

    @Override
    public void validate()
    {
        if (params.getLogin() == null || params.getPassword() == null)
        {
            throw new BadRequestParameterException("Parameters login and password are required.");
        }
    }
}
