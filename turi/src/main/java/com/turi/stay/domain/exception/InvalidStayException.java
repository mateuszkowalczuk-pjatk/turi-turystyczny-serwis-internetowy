package com.turi.stay.domain.exception;

import java.io.Serial;

public final class InvalidStayException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -3129993316454833457L;

    public InvalidStayException()
    {
        super("The specified stay is invalid. Parameters touristicPlaceI, name, description, price, peopleNumber and dateFrom are required!");
    }
}
