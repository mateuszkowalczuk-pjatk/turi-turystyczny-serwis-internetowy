package com.turi.premium.infrastructure.adapter.interfaces;

import com.turi.premium.domain.port.PremiumService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PremiumStatusCheckSchedule
{
    private final PremiumService service;

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateAllPremiumStatusAndAccountTypeIfPremiumExpired()
    {
        service.updateAllPremiumStatusAndAccountTypeIfPremiumExpired();
    }
}
