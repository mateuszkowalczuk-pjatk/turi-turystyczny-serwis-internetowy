package com.turi.image.infrastructure.adapter.interfaces;

import com.turi.image.domain.model.Image;
import com.turi.image.domain.model.ImageMode;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/image", produces = "application/json")
public class ImageRestController
{
    private final ImageFacade facade;

    @GetMapping("/getByAccount")
    public ResponseEntity<Image> getImageByAccountId()
    {
        return facade.getImageByAccount();
    }

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

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadImage(@RequestParam final MultipartFile file,
                                              @RequestParam final ImageMode mode,
                                              @RequestParam(required = false) final String id)
    {
        return facade.uploadImage(file, mode, id);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<?> deleteImageById(@PathVariable final String id)
    {
        return facade.deleteImageById(id);
    }
}
