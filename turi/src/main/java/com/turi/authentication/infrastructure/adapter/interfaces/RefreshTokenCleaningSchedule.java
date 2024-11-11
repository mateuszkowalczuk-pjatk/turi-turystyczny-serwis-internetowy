package com.turi.authentication.infrastructure.adapter.interfaces;

import com.turi.authentication.domain.port.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RefreshTokenCleaningSchedule
{
    private final RefreshTokenService service;

    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteAllExpiredRefreshTokens()
    {
        service.deleteAllExpiredRefreshTokens();
    }
}
