package com.turi.user.domain.exception;

import java.io.Serial;

public final class UserResetCodeRecentlySentException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 4778166757124445357L;

    public UserResetCodeRecentlySentException()
    {
        super("The user reset code cannot be sent again because it was sent less than 5 minutes ago.");
    }
}
