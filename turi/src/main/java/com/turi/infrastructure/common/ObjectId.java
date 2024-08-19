package com.turi.infrastructure.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class ObjectId
{
    private final long value;

    public static ObjectId of(final String value)
    {
        return new ObjectId(Long.parseLong(value));
    }

    public String toRest()
    {
        return String.valueOf(value);
    }
}