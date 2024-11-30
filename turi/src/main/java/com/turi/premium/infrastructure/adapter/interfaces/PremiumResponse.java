package com.turi.premium.infrastructure.adapter.interfaces;

import com.turi.infrastructure.exception.BadRequestResponseException;
import com.turi.premium.domain.model.Premium;
import com.turi.premium.domain.model.PremiumOffer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PremiumResponse
{
    public static ResponseEntity<PremiumOffer> of(final PremiumOffer offer)
    {
        if (offer == null)
        {
            throw new BadRequestResponseException("Premium offer response must not be null.");
        }

        return ResponseEntity.ok(offer);
    }

    public static ResponseEntity<Premium> of(final Premium premium)
    {
        if (premium == null)
        {
            throw new BadRequestResponseException("Premium response must not be null.");
        }

        return ResponseEntity.ok(premium);
    }

    public static ResponseEntity<Boolean> of(final Boolean result)
    {
        if (result == null)
        {
            throw new BadRequestResponseException("Premium check result response must not be null.");
        }

        return ResponseEntity.ok(result);
    }

    public static ResponseEntity<String> of(final String value)
    {
        if (value == null)
        {
            throw new BadRequestResponseException("Premium value response must not be null.");
        }

        return ResponseEntity.ok(value);
    }
}
