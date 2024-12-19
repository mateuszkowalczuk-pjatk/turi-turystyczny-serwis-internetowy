package com.turi.stay.domain.exception;

import java.io.Serial;

public final class InvalidStayInformationException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 3611705918194953825L;

    public InvalidStayInformationException()
    {
        super("The specified stay information is invalid. Parameters stayId and information are required!");
    }
}
