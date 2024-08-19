package com.turi.infrastructure.crud.queries;

public interface QueryBus<Q extends Query, T>
{
    T process(Q query);
}
