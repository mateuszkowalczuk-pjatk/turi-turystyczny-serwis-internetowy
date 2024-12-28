package com.turi.image.domain.exception;

import java.io.Serial;

public final class ImageStorageModeException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -8235931171903824837L;

    public ImageStorageModeException(final String mode)
    {
        super(String.format("Specified image mode '%s' is invalid.", mode));
    }
}
