import { useEffect } from 'react'
import { Image, ImageMode } from '../types/image.ts'
import { imageService } from '../services/imageService.ts'

export const useImage = (
    id: number,
    mode: ImageMode,
    setImages: (value: ((prevState: Image[]) => Image[]) | Image[]) => void
) => {
    useEffect(() => {
        const handleImageMode = async (mode: ImageMode): Promise<Response> => {
            switch (mode) {
                case ImageMode.TOURISTICPLACE:
                    return await imageService.getAllByTouristicPlaceId(id)
                case ImageMode.STAY:
                    return await imageService.getAllByStayId(id)
                case ImageMode.ATTRACTION:
                    return await imageService.getAllByAttractionId(id)
                default:
                    throw new Error('Invalid ImageMode')
            }
        }

        const fetchImages = async () => {
            const imagesResponse = await handleImageMode(mode)
            if (imagesResponse.status === 200) {
                const imagesResult = await imagesResponse.json()
                setImages(imagesResult)
            }
        }

        fetchImages().catch((error) => error)
    }, [setImages])
}
