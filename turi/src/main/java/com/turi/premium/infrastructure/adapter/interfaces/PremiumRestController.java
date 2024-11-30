package com.turi.premium.infrastructure.adapter.interfaces;

import com.turi.payment.domain.model.PaymentMethod;
import com.turi.premium.domain.model.Premium;
import com.turi.premium.domain.model.PremiumCompanyParam;
import com.turi.premium.domain.model.PremiumOffer;
import com.turi.premium.domain.model.PremiumVerifyParam;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/premium", produces = "application/json")
public class PremiumRestController
{
    private final PremiumFacade facade;

    @GetMapping("/getOffer")
    public ResponseEntity<PremiumOffer> getPremiumOffer()
    {
        return facade.getPremiumOffer();
    }

    @GetMapping("/getByAccount")
    public ResponseEntity<Premium> getPremiumByAccount()
    {
        return facade.getPremiumByAccount();
    }

    @GetMapping("/isExistsForAccount")
    public ResponseEntity<Boolean> isPremiumExistsForAccount()
    {
        return facade.isPremiumExistsForAccount();
    }

    @GetMapping("/verify")
    public ResponseEntity<Premium> verifyPremium(@RequestBody final PremiumVerifyParam params)
    {
        return facade.verifyPremium(params);
    }

    @GetMapping("/checkPayment")
    public ResponseEntity<Premium> checkPaymentForPremium()
    {
        return facade.checkPaymentForPremium();
    }

    @PostMapping("/pay")
    public ResponseEntity<String> payForPremium(@RequestBody final PaymentMethod method)
    {
        return facade.payForPremium(method);
    }

    @PutMapping("/renew")
    public ResponseEntity<Premium> renewPremium(@RequestBody final PaymentMethod method)
    {
        return facade.renewPremium(method);
    }

    @PutMapping("/cancel")
    public ResponseEntity<Premium> cancelPremium()
    {
        return facade.cancelPremium();
    }

    @PutMapping("/updateCompanyDetails")
    public ResponseEntity<Premium> updatePremiumCompanyDetails(@RequestBody final PremiumCompanyParam params)
    {
        return facade.updatePremiumCompanyDetails(params);
    }
}
