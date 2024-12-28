package com.turi.image.infrastructure.adapter.interfaces;

import com.turi.testhelper.annotation.RestControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RestControllerTest
class ImageFacadeTest
{
    @Autowired(required = false)
    private ImageFacade facade;

    @Test
    void testImage_GetImageByAccountId()
    {

    }

    @Test
    void testImage_GetImageByAccountId_ContextAccountIdIsNull()
    {

    }

    @Test
    void testImage_GetImageByAccountId_NotFound()
    {

    }

    @Test
    void testImage_GetImageAllByTouristicPlaceId()
    {

    }

    @Test
    void testImage_GetImageAllByTouristicPlaceId_WithoutRequiredTouristicPlaceIdParameter()
    {

    }

    @Test
    void testImage_GetImageAllByTouristicPlaceId_NothingFound()
    {

    }

    @Test
    void testImage_GetImageAllByStayId()
    {

    }

    @Test
    void testImage_GetImageAllByStayId_WithoutRequiredStayIdParameter()
    {

    }

    @Test
    void testImage_GetImageAllByStayId_NothingFound()
    {

    }

    @Test
    void testImage_GetImageAllByAttractionId()
    {

    }

    @Test
    void testImage_GetImageAllByAttractionId_WithoutRequiredAttractionIdParameter()
    {

    }

    @Test
    void testImage_GetImageAllByAttractionId_NothingFound()
    {

    }

    @Test
    void testImage_UploadImage_ForAccount()
    {

    }

    @Test
    void testImage_UploadImage_ReplaceForAccount()
    {

    }

    @Test
    void testImage_UploadImage_ForTouristicPlace()
    {

    }

    @Test
    void testImage_UploadImage_ForStay()
    {

    }

    @Test
    void testImage_UploadImage_ForAttraction()
    {

    }

    @Test
    void testImage_DeleteImage()
    {

    }

    @Test
    void testImage_DeleteImage_NothingToDelete()
    {

    }

    @Test
    void testImage_DeleteAllImagesByStayId()
    {

    }

    @Test
    void testImage_DeleteAllImagesByStayId_NothingToDelete()
    {

    }

    @Test
    void testImage_DeleteAllImagesByAttractionId()
    {

    }

    @Test
    void testImage_DeleteAllImagesByAttractionId_NothingToDelete()
    {

    }
}
