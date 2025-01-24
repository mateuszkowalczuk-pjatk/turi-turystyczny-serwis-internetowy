package com.turi.reservation.infrastructure.adapter.interfaces;

import com.turi.reservation.domain.port.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReservationReminderSchedule
{
    private final ReservationService service;

    @Scheduled(cron = "0 0 * * * ?")
    public void createAllReservationsReminder()
    {
        service.createAllReservationsReminder();
    }
}
