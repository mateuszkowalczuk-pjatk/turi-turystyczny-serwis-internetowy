package com.turi.owner.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serial;

public final class OwnerNotFoundException extends ResponseStatusException
{
    @Serial
    private static final long serialVersionUID = 8133350796531917582L;

    public OwnerNotFoundException()
    {
        super(HttpStatus.BAD_REQUEST, "The specified owner is invalid. ownerId, firstName, lastName, description, phoneNumber and email are required!");
    }
}
