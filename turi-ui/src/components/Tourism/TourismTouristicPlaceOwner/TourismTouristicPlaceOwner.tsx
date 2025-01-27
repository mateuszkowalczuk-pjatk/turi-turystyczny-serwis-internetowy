import { ReactNode, useEffect, useState } from 'react'
import { useHooks } from '../../../hooks/shared/useHooks.ts'
import Label from '../../Shared/Controls/Label'
import ImagePanel from '../../Shared/Image/ImagePanel'
import ImageUploader from '../../Shared/Image/ImageUploader'
import { Image, ImageMode } from '../../../types/image.ts'
import { imageService } from '../../../services/imageService.ts'
import styles from './TourismTouristicPlaceOwner.module.css'

interface Props {
    firstPanel: ReactNode
    secondPanel: ReactNode
    thirdPanel: ReactNode
}

const TourismTouristicPlaceOwner = ({ firstPanel, secondPanel, thirdPanel }: Props) => {
    const { t } = useHooks()
    const [image, setImage] = useState<Image | null>(null)

    useEffect(() => {
        fetchAccountImage().catch((error) => error)
    }, [])

    const handleImageUpload = (path: { path: string }) => {
        if (path != null) fetchAccountImage().catch((error) => error)
    }

    const fetchAccountImage = async () => {
        const response = await imageService.getByAccount()
        if (response.status === 200) {
            const accountImage = await response.json()
            setImage(accountImage)
        }
    }

    const removeImage = (imageId: number) => {
        if (image !== null && image.imageId === imageId) setImage(null)
    }

    return (
        <div className={styles.owner}>
            <Label text={t('tourism.touristic-place-owner')} />
            <div className={styles.details}>
                {image ? (
                    <ImagePanel
                        id={image.imageId}
                        path={image.path}
                        removeImage={removeImage}
                    />
                ) : (
                    <ImageUploader
                        uploadImage={handleImageUpload}
                        mode={ImageMode.ACCOUNT}
                    />
                )}
                <div className={styles.right}>
                    {firstPanel}
                    {secondPanel}
                </div>
            </div>
            {thirdPanel}
        </div>
    )
}

export default TourismTouristicPlaceOwner
