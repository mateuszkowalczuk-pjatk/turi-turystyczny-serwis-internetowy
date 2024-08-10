package com.turi.authentication.infrastructure.adapter.application.queries.registration;

import com.turi.infrastructure.crud.Validator;
import com.turi.infrastructure.crud.queries.Query;
import com.turi.infrastructure.exception.BadRequestParameterException;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class RegistrationQuery implements Query, Validator
{
    private final RegistrationParam params;

    public RegistrationParam register()
    {
        return params;
    }

    @Override
    public void validate()
    {
        if (params.getUsername() == null || params.getEmail() == null || params.getPassword() == null)
        {
            throw new BadRequestParameterException("Parameters login, email and password are required.");
        }
    }
}
