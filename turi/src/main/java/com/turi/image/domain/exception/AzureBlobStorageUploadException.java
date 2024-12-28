package com.turi.image.domain.exception;

import java.io.Serial;

public final class AzureBlobStorageUploadException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 4586592513556528205L;

    public AzureBlobStorageUploadException(final String message)
    {
        super(String.format("Something went wrong during uploading image to Azure Blob Storage! '%s'", message));
    }
}
