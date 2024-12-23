package com.turi.image.domain.exception;

import java.io.Serial;

public final class InvalidImageNameException  extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 9034754035434547841L;

    public InvalidImageNameException()
    {
        super("Image name must not be null!");
    }
}
