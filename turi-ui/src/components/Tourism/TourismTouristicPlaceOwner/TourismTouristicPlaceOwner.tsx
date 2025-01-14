import { ReactNode, useEffect, useState } from 'react'
import { useTranslation } from 'react-i18next'
import ImageUploader from '../../Shared/Image/ImageUploader'
import PersonalLabel from '../../Shared/Personal/PersonalLabel'
import ImagePanel from '../../Shared/Image/ImagePanel'
import { Image, ImageMode } from '../../../types/image.ts'
import { imageService } from '../../../services/imageService.ts'
import styles from './TourismTouristicPlaceOwner.module.css'

interface Props {
    firstPanel: ReactNode
    secondPanel: ReactNode
    thirdPanel: ReactNode
}

const TourismTouristicPlaceOwner = ({ firstPanel, secondPanel, thirdPanel }: Props) => {
    const { t } = useTranslation()
    const [image, setImage] = useState<Image | null>(null)

    useEffect(() => {
        fetchAccountImage().catch((error) => error)
    }, [])

    const handleImageUpload = (path: { path: string }) => {
        if (path != null) fetchAccountImage().catch((error) => error)
    }

    const fetchAccountImage = async () => {
        const response = await imageService.getByAccountId()
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
            <PersonalLabel text={t('tourism.touristic-place-owner')} />
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
