package com.turi.authentication.infrastructure.adapter.application.queries.logout;

import com.turi.infrastructure.crud.Validator;
import com.turi.infrastructure.crud.queries.Query;
import com.turi.infrastructure.exception.BadRequestParameterException;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class LogoutQuery implements Query, Validator
{
    private final LogoutParam params;

    public LogoutParam logout()
    {
        return params;
    }

    @Override
    public void validate()
    {
        if (params.getRefreshToken() == null || params.getResponse() == null)
        {
            throw new BadRequestParameterException("Parameters refreshToken and response are required.");
        }
    }
}
