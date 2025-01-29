package com.turi.image.infrastructure.adapter.service;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.turi.image.domain.exception.AzureBlobStorageUploadException;
import com.turi.image.domain.port.AzureBlobStorageService;
import com.turi.image.infrastructure.config.AzureStorageProperties;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@AllArgsConstructor
public class AzureBlobStorageServiceImpl implements AzureBlobStorageService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AzureBlobStorageServiceImpl.class);
    private final AzureStorageProperties properties;

    @Override
    public String upload(final MultipartFile file, final String fileName)
    {
        LOGGER.info("Starting file upload: {}", fileName);
        final var containerClient = getBlobServiceClient();

        if (!containerClient.exists())
        {
            LOGGER.info("Container does not exist, creating container: {}", properties.getContainerName());
            containerClient.create();
        }

        final var client = containerClient.getBlobClient(fileName);
        LOGGER.info("Blob client created for file: {}", fileName);

        try
        {
            client.upload(file.getInputStream(), file.getSize(), true);
            LOGGER.info("File uploaded successfully: {}", fileName);
        }
        catch (final IOException ex)
        {
            LOGGER.error("Error uploading file to Azure Blob Storage: {}", ex.getMessage(), ex);
            throw new AzureBlobStorageUploadException(ex.getMessage());
        }

        return fileName;
    }

    @Override
    public void delete(final String path)
    {
        final var containerClient = getBlobServiceClient();

        containerClient.getBlobClient(path).delete();
    }

    private BlobContainerClient getBlobServiceClient()
    {
        final var connectionString = String.format(
                "DefaultEndpointsProtocol=https;AccountName=%s;AccountKey=%s;EndpointSuffix=core.windows.net",
                properties.getAccountName(),
                properties.getAccountKey()
        );

        final var blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();

        return blobServiceClient.getBlobContainerClient(properties.getContainerName());
    }
}
