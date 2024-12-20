package com.turi.image.infrastructure.adapter.service;

import com.turi.image.domain.model.Image;
import com.turi.image.domain.port.ImageRepository;
import com.turi.image.domain.port.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService
{
    private final ImageRepository repository;

    @Override
    public Image getByAccountId(final Long accountId)
    {
        return repository.findByAccountId(accountId);
    }

    @Override
    public List<Image> getAllByTouristicPlaceId(final Long touristicPlaceId)
    {
        return repository.findAllByTouristicPlaceId(touristicPlaceId);
    }

    @Override
    public List<Image> getAllByStayId(final Long stayId)
    {
        return repository.findAllByStayId(stayId);
    }

    @Override
    public List<Image> getAllByAttractionId(final Long attractionId)
    {
        return repository.findAllByAttractionId(attractionId);
    }

    @Override
    public void createForAccount(final Long accountId, final String path)
    {
        final var image = Image.builder()
                .withAccountId(accountId)
                .withPath(path)
                .build();

        repository.insert(image);
    }

    @Override
    public void createForTouristicPlace(final Long touristicPlaceId, final String path)
    {
        final var image = Image.builder()
                .withTouristicPlaceId(touristicPlaceId)
                .withPath(path)
                .build();

        repository.insert(image);
    }

    @Override
    public void createForStay(final Long stayId, final String path)
    {
        final var image = Image.builder()
                .withStayId(stayId)
                .withPath(path)
                .build();

        repository.insert(image);
    }

    @Override
    public void createForAttraction(final Long attractionId, final String path)
    {
        final var image = Image.builder()
                .withAttractionId(attractionId)
                .withPath(path)
                .build();

        repository.insert(image);
    }

    @Override
    public void deleteById(final Long id)
    {
        repository.deleteById(id);
    }

    @Override
    public void deleteAllByStayId(final Long stayId)
    {
        repository.deleteAllByStayId(stayId);
    }

    @Override
    public void deleteAllByAttractionId(final Long attractionId)
    {
        repository.deleteAllByAttractionId(attractionId);
    }
}
