package com.turi.user.infrastructure.adapter.interfaces;

import com.turi.user.domain.port.UserService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserPasswordResetDetailsCleaningSchedule
{
    private final UserService service;

    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteAllUserPasswordResetDetails()
    {
        service.deleteAllPasswordResetDetails();
    }
}
