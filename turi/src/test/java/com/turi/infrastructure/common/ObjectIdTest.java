package com.turi.infrastructure.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ObjectIdTest
{
    @Test
    void testObjectId()
    {
        final var objectId = ObjectId.of("1");
        assertThat(objectId.getValue()).isEqualTo(1);
    }

    @Test
    void testObjectId_ToRest()
    {
        final var objectId = ObjectId.of("1");
        assertThat(objectId.toRest()).isEqualTo("1");
    }

    @Test
    void testObjectId_IsNull()
    {
        assertThrows(NumberFormatException.class, () -> ObjectId.of(null));
    }

    @Test
    void testObjectId_IsEmpty()
    {
        assertThrows(NumberFormatException.class, () -> ObjectId.of(""));
    }
}
