package com.turi.stay.infrastructure.adapter.interfaces;

import com.turi.stay.domain.model.Stay;
import com.turi.stay.domain.model.StayDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/stay", produces = "application/json")
public class StayRestController
{
    private final StayFacade facade;

    @GetMapping("/getAllByTouristicPlaceId")
    public ResponseEntity<List<StayDto>> getAllStaysByTouristicPlaceId(@RequestParam final String touristicPlaceId)
    {
        return facade.getAllStaysByTouristicPlaceId(touristicPlaceId);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<StayDto> getStayById(@PathVariable final String id)
    {
        return facade.getStayById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createStay(@RequestBody final StayDto stay)
    {
        return facade.createStay(stay);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateStay(@PathVariable final String id,
                                        @RequestBody final Stay stay)
    {
        return facade.updateStay(id, stay);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStay(@PathVariable final String id)
    {
        return facade.deleteStay(id);
    }
}
