package com.turi.user.infrastructure.adapter.interfaces;

import com.turi.user.domain.model.User;
import io.jsonwebtoken.lang.Assert;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserResponse
{
    public static User of(final User user)
    {
        Assert.notNull(user, "User must not be null.");

        return user;
    }

    public static Boolean of(final Boolean result)
    {
        Assert.notNull(result, "Check result must not be null.");

        return result;
    }
}
