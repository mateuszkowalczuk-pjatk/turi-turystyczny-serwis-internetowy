package com.turi.authentication.infrastructure.adapter.application.queries.refresh;

import com.turi.infrastructure.crud.Validator;
import com.turi.infrastructure.crud.queries.Query;
import com.turi.infrastructure.exception.BadRequestParameterException;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class RefreshQuery implements Query, Validator
{
    private final RefreshParam params;

    public RefreshParam refresh()
    {
        return params;
    }

    @Override
    public void validate()
    {
        if (params.getRefreshToken() == null)
        {
            throw new BadRequestParameterException("Parameter refreshToken is required.");
        }
    }
}
