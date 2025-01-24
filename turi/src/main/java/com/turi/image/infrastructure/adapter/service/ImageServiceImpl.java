package com.turi.image.infrastructure.adapter.service;

import com.turi.image.domain.exception.ImageNotFoundForAccountException;
import com.turi.image.domain.exception.InvalidImageNameException;
import com.turi.image.domain.model.Image;
import com.turi.image.domain.model.ImageMode;
import com.turi.image.domain.port.ImageRepository;
import com.turi.image.domain.port.ImageService;
import com.turi.image.domain.port.StorageService;
import com.turi.image.infrastructure.config.ImageStorageProperties;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService
{
    private final ImageRepository repository;
    private final StorageService storageService;
    private final ImageStorageProperties properties;

    @Override
    public Image getByAccountId(final Long accountId)
    {
        final var image = repository.findByAccountId(accountId);

        if (image == null)
        {
            throw new ImageNotFoundForAccountException(accountId);
        }

        image.setPath(generateImageUrl(image.getPath()));

        return image;
    }

    @Override
    public List<Image> getAllByTouristicPlaceId(final Long touristicPlaceId)
    {
        return repository.findAllByTouristicPlaceId(touristicPlaceId).stream()
                .peek(image -> image.setPath(generateImageUrl(image.getPath())))
                .collect(Collectors.toList());
    }

    @Override
    public List<Image> getAllByStayId(final Long stayId)
    {
        return repository.findAllByStayId(stayId).stream()
                .peek(image -> image.setPath(generateImageUrl(image.getPath())))
                .collect(Collectors.toList());
    }

    @Override
    public List<Image> getAllByAttractionId(final Long attractionId)
    {
        return repository.findAllByAttractionId(attractionId).stream()
                .peek(image -> image.setPath(generateImageUrl(image.getPath())))
                .collect(Collectors.toList());
    }

    @Override
    public String upload(final MultipartFile file, final ImageMode mode, final Long id)
    {
        final var fileName = generateUniqueFileName(file.getOriginalFilename());

        final var path = storageService.upload(file, fileName);

        if (path == null)
        {
            return null;
        }

        createImage(fileName, mode, id);

        return generateImageUrl(path);
    }

    private String generateUniqueFileName(final String fileName)
    {
        if (fileName == null)
        {
            throw new InvalidImageNameException();
        }

        return UUID.randomUUID() + fileName.substring(fileName.lastIndexOf("."));
    }

    private void createImage(final String fileName, final ImageMode mode, final Long id)
    {
        if (mode.equals(ImageMode.ACCOUNT)) createAccountImage(fileName, id);
        else if (mode.equals(ImageMode.TOURISTICPLACE)) createTouristicPlaceImage(fileName, id);
        else if (mode.equals(ImageMode.STAY)) createStayImage(fileName, id);
        else if (mode.equals(ImageMode.ATTRACTION)) createAttractionImage(fileName, id);
    }

    private void createAccountImage(final String fileName, final Long accountId)
    {
        final var accountImage = repository.findByAccountId(accountId);

        if (accountImage != null)
        {
            deleteById(accountImage.getImageId());
        }

        final var image = Image.builder()
                .withAccountId(accountId)
                .withPath(fileName)
                .build();

        repository.insert(image);
    }

    private void createTouristicPlaceImage(final String fileName, final Long touristicPlaceId)
    {
        final var image = Image.builder()
                .withTouristicPlaceId(touristicPlaceId)
                .withPath(fileName)
                .build();

        repository.insert(image);
    }

    private void createStayImage(final String fileName, final Long stayId)
    {
        final var image = Image.builder()
                .withStayId(stayId)
                .withPath(fileName)
                .build();

        repository.insert(image);
    }

    private void createAttractionImage(final String fileName, final Long attractionId)
    {
        final var image = Image.builder()
                .withAttractionId(attractionId)
                .withPath(fileName)
                .build();

        repository.insert(image);
    }

    private String generateImageUrl(final String path)
    {
        final var url = "azure".equalsIgnoreCase(properties.getMode())
                ? properties.getAzureUrl()
                : properties.getLocalUrl();

        return url + path;
    }

    @Override
    public void deleteById(final Long id)
    {
        final var path = repository.deleteById(id);

        storageService.delete(path);
    }

    @Override
    public void deleteAllByStayId(final Long stayId)
    {
        getAllByStayId(stayId).forEach(image -> repository.deleteById(image.getImageId()));
    }

    @Override
    public void deleteAllByAttractionId(final Long attractionId)
    {
        getAllByAttractionId(attractionId).forEach(image -> repository.deleteById(image.getImageId()));
    }
}
