package com.turi.image.domain.exception;

import java.io.Serial;

public final class ImageNotFoundForAccountException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -3045359589806520689L;

    public ImageNotFoundForAccountException(final Long accountId)
    {
        super(String.format("Image for account with Id '%s' not found.", accountId));
    }
}
