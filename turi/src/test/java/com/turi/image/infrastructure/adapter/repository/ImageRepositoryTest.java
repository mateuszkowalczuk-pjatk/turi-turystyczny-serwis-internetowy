package com.turi.image.infrastructure.adapter.repository;

import com.turi.image.domain.port.ImageRepository;
import com.turi.testhelper.annotation.RepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class ImageRepositoryTest
{
    @Autowired
    private ImageRepository repository;

    @Test
    void testImage_FindById()
    {

    }

    @Test
    void testImage_FindById_NotFound()
    {

    }

    @Test
    void testImage_FindByAccountId()
    {

    }

    @Test
    void testImage_FindByAccountId_NotFound()
    {

    }

    @Test
    void testImage_FindAllByTouristicPlaceId()
    {

    }

    @Test
    void testImage_FindAllByTouristicPlaceId_NothingFound()
    {

    }

    @Test
    void testImage_FindAllByStayId()
    {

    }

    @Test
    void testImage_FindAllByStayId_NothingFound()
    {

    }

    @Test
    void testImage_FindAllByAttractionId()
    {

    }

    @Test
    void testImage_FindAllByAttractionId_NothingFound()
    {

    }

    @Test
    void testImage_Insert()
    {

    }

    @Test
    void testImage_Insert_WithoutRequiredPathField()
    {

    }

    @Test
    void testImage_Delete()
    {

    }

    @Test
    void testImage_Delete_NothingToDelete()
    {

    }
}
