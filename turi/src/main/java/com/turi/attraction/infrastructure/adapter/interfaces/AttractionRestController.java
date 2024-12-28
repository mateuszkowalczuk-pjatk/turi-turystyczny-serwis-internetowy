package com.turi.attraction.infrastructure.adapter.interfaces;

import com.turi.attraction.domain.model.Attraction;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/attraction", produces = "application/json")
public class AttractionRestController
{
    private final AttractionFacade facade;

    @GetMapping("/getAllByTouristicPlaceId")
    public ResponseEntity<List<Attraction>> getAllAttractionsByTouristicPlaceId(@RequestParam final String touristicPlaceId)
    {
        return facade.getAllAttractionsByTouristicPlaceId(touristicPlaceId);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Attraction> getAttractionById(@PathVariable final String id)
    {
        return facade.getAttractionById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<Attraction> createAttraction(@RequestBody final Attraction attraction)
    {
        return facade.createAttraction(attraction);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAttraction(@PathVariable final String id,
                                              @RequestBody final Attraction attraction)
    {
        return facade.updateAttraction(id, attraction);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAttraction(@PathVariable final String id)
    {
        return facade.deleteAttraction(id);
    }
}
