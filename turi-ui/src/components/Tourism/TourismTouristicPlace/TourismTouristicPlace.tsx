import { useState } from 'react'
import { useImage } from '../../../hooks/useImage.ts'
import ImageBanner from '../../Shared/Image/ImageBanner'
import TourismTouristicPlaceForm from '../TourismTouristicPlaceForm'
import { Image, ImageMode } from '../../../types/image.ts'
import styles from './TourismTouristicPlace.module.css'

const TourismTouristicPlace = ({ touristicPlaceId }: { touristicPlaceId: number }) => {
    const [images, setImages] = useState<Image[]>([])

    useImage(touristicPlaceId, ImageMode.TOURISTICPLACE, setImages)

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
