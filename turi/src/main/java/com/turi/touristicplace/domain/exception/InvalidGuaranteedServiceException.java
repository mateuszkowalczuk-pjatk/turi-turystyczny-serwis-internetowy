package com.turi.touristicplace.domain.exception;

import java.io.Serial;

public final class InvalidGuaranteedServiceException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -6380790134665429878L;

    public InvalidGuaranteedServiceException()
    {
        super("The specified touristic place guaranteed service is invalid. Parameters touristicPlaceId and service are required!");
    }
}
