package com.turi.touristicplace.infrastructure.adapter.interfaces;

import com.turi.touristicplace.domain.model.GuaranteedService;
import com.turi.touristicplace.domain.model.TouristicPlace;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/touristicplace", produces = "application/json")
public class TouristicPlaceRestController
{
    private final TouristicPlaceFacade facade;

    @GetMapping("/getByPremiumId")
    public ResponseEntity<TouristicPlace> getTouristicPlaceByPremiumId()
    {
        return facade.getByTouristicPlacePremiumId();
    }

    @GetMapping("/isAddressExists")
    public ResponseEntity<Boolean> isTouristicPlaceAddressExists(@RequestParam final String country,
                                                                 @RequestParam final String city,
                                                                 @RequestParam final String zipCode,
                                                                 @RequestParam final String street,
                                                                 @RequestParam final String buildingNumber,
                                                                 @RequestParam(required = false) final String apartmentNumber)
    {
        return facade.isTouristicPlaceAddressExists(country, city, zipCode, street, buildingNumber, apartmentNumber);
    }

    @GetMapping("/getAllGuaranteedServices")
    public ResponseEntity<List<GuaranteedService>> getAllTouristicPlaceGuaranteedServices()
    {
        return facade.getAllTouristicPlaceGuaranteedServices();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTouristicPlace()
    {
        return facade.createTouristicPlace();
    }

    @PostMapping("/createGuaranteedService")
    public ResponseEntity<GuaranteedService> createTouristicPlaceGuaranteedService(@RequestBody final GuaranteedService guaranteedService)
    {
        return facade.createTouristicPlaceGuaranteedService(guaranteedService);
    }

    @PutMapping("/updateDetails/{id}")
    public ResponseEntity<?> updateTouristicPlaceDetails(@PathVariable final String id,
                                                         @RequestBody final TouristicPlace touristicPlace)
    {
        return facade.updateTouristicPlaceDetails(id, touristicPlace);
    }

    @DeleteMapping("/deleteGuaranteedService")
    public ResponseEntity<?> deleteTouristicPlaceGuaranteedService(@RequestParam final String guaranteedServiceId)
    {
        return facade.deleteTouristicPlaceGuaranteedService(guaranteedServiceId);
    }
}
