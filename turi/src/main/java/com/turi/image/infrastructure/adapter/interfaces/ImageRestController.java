package com.turi.image.infrastructure.adapter.interfaces;

import com.turi.image.domain.model.Image;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/image", produces = "application/json")
public class ImageRestController
{
    private final ImageFacade facade;

    @GetMapping("/getByAccountId")
    public ResponseEntity<Image> getImageByAccountId(@RequestParam final String accountId)
    {
        return facade.getImageByAccountId(accountId);
    }

    @GetMapping("/getAllByTouristicPlaceId")
    public ResponseEntity<List<Image>> getAllImagesByTouristicPlaceId(@RequestParam final String touristicPlaceId)
    {
        return facade.getAllImagesByTouristicPlaceId(touristicPlaceId);
    }

    @GetMapping("/getAllByStayId")
    public ResponseEntity<List<Image>> getAllImagesByStayId(@RequestParam final String stayId)
    {
        return facade.getAllImagesByStayId(stayId);
    }

    @GetMapping("/getAllByAttractionId")
    public ResponseEntity<List<Image>> getAllImagesByAttractionId(@RequestParam final String attractionId)
    {
        return facade.getAllImagesByAttractionId(attractionId);
    }

    @PostMapping("/createForAccount")
    public ResponseEntity<?> createImageForAccount(@RequestParam final String accountId,
                                                   @RequestParam final String path)
    {
        return facade.createImageForAccount(accountId, path);
    }

    @PostMapping("/createForTouristicPlace")
    public ResponseEntity<?> createImageForTouristicPlace(@RequestParam final String touristicPlaceId,
                                                          @RequestParam final String path)
    {
        return facade.createImageForTouristicPlace(touristicPlaceId, path);
    }

    @PostMapping("/createForStay")
    public ResponseEntity<?> createImageForStay(@RequestParam final String stayId,
                                                @RequestParam final String path)
    {
        return facade.createImageForStay(stayId, path);
    }

    @PostMapping("/createForAttraction")
    public ResponseEntity<?> createImageForAttraction(@RequestParam final String attractionId,
                                                      @RequestParam final String path)
    {
        return facade.createImageForAttraction(attractionId, path);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<?> deleteImageById(@PathVariable final String id)
    {
        return facade.deleteImageById(id);
    }
}
