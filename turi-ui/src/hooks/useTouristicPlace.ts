import { useEffect } from 'react'
import { Image } from '../types/image.ts'
import { touristicPlaceService } from '../services/touristicPlaceService.ts'
import { imageService } from '../services/imageService.ts'

export const useTouristicPlace = (
    setTouristicPlaceId: (value: ((prevState: number | undefined) => number | undefined) | number | undefined) => void,
    setImages: (value: ((prevState: Image[]) => Image[]) | Image[]) => void
) => {
    useEffect(() => {
        const fetchTouristicPlace = async () => {
            const touristicPlaceResponse = await touristicPlaceService.getByPremiumId()
            if (touristicPlaceResponse.status === 200) {
                const touristicPlaceResult = await touristicPlaceResponse.json()
                setTouristicPlaceId(touristicPlaceResult.touristicPlaceId)

                const touristicPlaceImagesResponse = await imageService.getAllByTouristicPlaceId(
                    touristicPlaceResult.touristicPlaceId
                )
                if (touristicPlaceImagesResponse.status === 200) {
                    const imagesResult = await touristicPlaceImagesResponse.json()
                    setImages(imagesResult)
                }
            }
        }

        fetchTouristicPlace().catch((error) => error)
    }, [setTouristicPlaceId, setImages])
}
