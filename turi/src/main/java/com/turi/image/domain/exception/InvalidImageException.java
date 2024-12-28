package com.turi.image.domain.exception;

import java.io.Serial;

public final class InvalidImageException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -6070321199953425596L;

    public InvalidImageException()
    {
        super("The specified image is invalid. Parameter path is required!");
    }
}
