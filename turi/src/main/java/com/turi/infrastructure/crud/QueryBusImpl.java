package com.turi.infrastructure.crud;

import com.turi.infrastructure.crud.queries.Query;
import com.turi.infrastructure.crud.queries.QueryBus;
import com.turi.infrastructure.crud.queries.QueryHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class QueryBusImpl<Q extends Query, T> implements QueryBus<Q, T>
{
    private final QueryHandler<Q, T> queryHandler;

    @Override
    public T process(Q query)
    {
        if (query instanceof final Validator request)
        {
            request.validate();
        }

        return queryHandler.execute(query);
    }
}
