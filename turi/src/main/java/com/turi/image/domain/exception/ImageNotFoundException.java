package com.turi.image.domain.exception;

import java.io.Serial;

public final class ImageNotFoundException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -4796056675568835515L;

    public ImageNotFoundException(final Long imageId)
    {
        super(String.format("Image with Id '%s' not found.", imageId));
    }
}
