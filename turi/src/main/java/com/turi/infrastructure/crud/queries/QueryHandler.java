package com.turi.infrastructure.crud.queries;

public interface QueryHandler<Q extends Query, T>
{
    T execute(Q query);
}
