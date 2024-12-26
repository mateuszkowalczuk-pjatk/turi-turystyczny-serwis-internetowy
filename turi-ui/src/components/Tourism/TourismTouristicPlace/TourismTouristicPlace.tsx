import { useState } from 'react'
import { useTouristicPlace } from '../../../hooks/useTouristicPlace.ts'
import ImageBanner from '../../Shared/Image/ImageBanner'
import TourismTouristicPlaceForm from '../TourismTouristicPlaceForm'
import { Image, ImageMode } from '../../../types/image.ts'
import styles from './TourismTouristicPlace.module.css'

const TourismTouristicPlace = () => {
    const [touristicPlaceId, setTouristicPlaceId] = useState<number>()
    const [images, setImages] = useState<Image[]>([])

    useTouristicPlace(setTouristicPlaceId, setImages)

    return (
        <div className={styles.touristicplace}>
            <ImageBanner
                images={images}
                uploadImage={(image: Image) => setImages((prevImages) => [...prevImages, image])}
                mode={ImageMode.TOURISTICPLACE}
                id={touristicPlaceId}
                removeImage={(imageId: number) =>
                    setImages((prevImage) => prevImage.filter((image) => image.imageId !== imageId))
                }
            />
            {touristicPlaceId && <TourismTouristicPlaceForm touristicPlaceId={touristicPlaceId} />}
        </div>
    )
}

export default TourismTouristicPlace
