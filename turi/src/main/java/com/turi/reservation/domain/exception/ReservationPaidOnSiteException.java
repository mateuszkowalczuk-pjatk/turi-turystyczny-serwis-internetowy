package com.turi.reservation.domain.exception;

import java.io.Serial;

public final class ReservationPaidOnSiteException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -8160901563772625626L;

    public ReservationPaidOnSiteException(final Long reservationId)
    {
        super(String.format("Reservation with Id '%s' is not to be paid on site.", reservationId));
    }
}
