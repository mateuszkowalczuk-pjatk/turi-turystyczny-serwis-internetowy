package com.turi.image.infrastructure.adapter.service;

import com.turi.image.domain.port.ImageService;
import com.turi.testhelper.annotation.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ServiceTest
class ImageServiceTest
{
    @Autowired
    private ImageService service;

    @Test
    void testImage_GetByAccountId()
    {

    }

    @Test
    void testImage_GetByAccountId_NotFound()
    {

    }

    @Test
    void testImage_GetAllByTouristicPlaceId()
    {

    }

    @Test
    void testImage_GetAllByTouristicPlaceId_NothingFound()
    {

    }

    @Test
    void testImage_GetAllByStayId()
    {

    }

    @Test
    void testImage_GetAllByStayId_NothingFound()
    {

    }

    @Test
    void testImage_GetAllByAttractionId()
    {

    }

    @Test
    void testImage_GetAllByAttractionId_NothingFound()
    {

    }

    @Test
    void testImage_Upload_ForAccount()
    {

    }

    @Test
    void testImage_Upload_ReplaceForAccount()
    {

    }

    @Test
    void testImage_Upload_ForTouristicPlace()
    {

    }

    @Test
    void testImage_Upload_ForStay()
    {

    }

    @Test
    void testImage_Upload_ForAttraction()
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

    @Test
    void testImage_DeleteAllByStayId()
    {

    }

    @Test
    void testImage_DeleteAllByStayId_NothingToDelete()
    {

    }

    @Test
    void testImage_DeleteAllByAttractionId()
    {

    }

    @Test
    void testImage_DeleteAllByAttractionId_NothingToDelete()
    {

    }
}
