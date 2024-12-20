package com.turi.image.infrastructure.adapter.repository;

import com.turi.image.domain.exception.ImageNotFoundException;
import com.turi.image.domain.model.Image;
import com.turi.image.domain.port.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class ImageRepositoryImpl implements ImageRepository
{
    private final ImageRepositoryDao repositoryDao;

    @Override
    public Image findById(final Long id)
    {
        return repositoryDao.findById(id)
                .map(Image::of)
                .orElseThrow(() -> new ImageNotFoundException(id));
    }

    @Override
    public Image findByAccountId(final Long accountId)
    {
        return repositoryDao.findByAccountId(accountId)
                .map(Image::of)
                .orElse(null);
    }

    @Override
    public List<Image> findAllByTouristicPlaceId(final Long touristicPlaceId)
    {
        return repositoryDao.findAllByTouristicPlaceId(touristicPlaceId).stream()
                .map(Image::of)
                .toList();
    }

    @Override
    public List<Image> findAllByStayId(final Long stayId)
    {
        return repositoryDao.findAllByStayId(stayId).stream()
                .map(Image::of)
                .toList();
    }

    @Override
    public List<Image> findAllByAttractionId(final Long attractionId)
    {
        return repositoryDao.findAllByAttractionId(attractionId).stream()
                .map(Image::of)
                .toList();
    }

    @Override
    public void insert(final Image image)
    {
        final var entity = ImageEntity.of(image);

        repositoryDao.saveAndFlush(entity);
    }

    @Override
    public void deleteById(final Long id)
    {
        final var image = findById(id);

        repositoryDao.deleteById(image.getImageId());
    }

    @Override
    public void deleteAllByStayId(final Long stayId)
    {
        repositoryDao.deleteAllByStayId(stayId);
    }

    @Override
    public void deleteAllByAttractionId(final Long attractionId)
    {
        repositoryDao.deleteAllByAttractionId(attractionId);
    }
}
