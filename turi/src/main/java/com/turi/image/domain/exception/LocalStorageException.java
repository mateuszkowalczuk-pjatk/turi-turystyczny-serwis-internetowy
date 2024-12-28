package com.turi.image.domain.exception;

import java.io.Serial;

public final class LocalStorageException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 8420294043003017804L;

    public LocalStorageException(final String message)
    {
        super(String.format("Something went wrong during uploading or removing image from local storage! '%s'", message));
    }
}
