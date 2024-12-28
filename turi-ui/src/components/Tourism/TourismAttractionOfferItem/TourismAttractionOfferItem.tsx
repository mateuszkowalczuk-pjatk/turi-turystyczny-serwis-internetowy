import { useEffect, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useNavigate } from 'react-router-dom'
import { GreyButton } from '../../Shared/Controls/Button'
import { Attraction } from '../../../types/attraction.ts'
import { Image } from '../../../types/image.ts'
import ImagePanel from '../../Shared/Image/ImagePanel'
import TextRegular from '../../Shared/Controls/Text/TextRegular'
import { imageService } from '../../../services/imageService.ts'
import styles from './TourismAttractionOfferItem.module.css'

interface Props {
    attraction: Attraction
    index: number
}

const TourismAttractionOfferItem = ({ attraction, index }: Props) => {
    const { t } = useTranslation()
    const navigate = useNavigate()
    const [image, setImage] = useState<Image>()

    useEffect(() => {
        const fetchImage = async () => {
            if (attraction.attractionId) {
                const imagesResponse = await imageService.getAllByAttractionId(attraction.attractionId)
                if (imagesResponse.status === 200) {
                    const imagesData = await imagesResponse.json()
                    setImage(imagesData[0])
                }
            }
        }

        fetchImage().catch((error) => error)
    }, [])

    return (
        <div
            className={styles.item}
            key={index}
        >
            <div className={styles.image}>
                {image && image.path && (
                    <ImagePanel
                        path={image.path}
                        onlyDisplay
                    />
                )}
            </div>
            <div className={styles.name}>
                <TextRegular text={attraction.name} />
                <TextRegular text={attraction.description} />
            </div>
            <div className={styles.details}>
                <TextRegular
                    text={
                        t('tourism.touristic-place-stay-offer-price-text') +
                        attraction.price +
                        t('tourism.touristic-place-stay-offer-currency') +
                        t(
                            attraction.priceType.toString() === 'HOUR'
                                ? 'tourism.touristic-place-attraction-price-hour'
                                : attraction.priceType.toString() === 'ITEM'
                                  ? 'tourism.touristic-place-attraction-price-item'
                                  : attraction.priceType.toString() === 'PERSON'
                                    ? 'tourism.touristic-place-attraction-price-people'
                                    : ''
                        )
                    }
                />
                {attraction.priceType.toString() === 'ITEM' && (
                    <TextRegular text={t('tourism.touristic-place-attraction-max-item') + attraction.maxItems} />
                )}
                {attraction.priceType.toString() === 'PERSON' && (
                    <TextRegular
                        text={t('tourism.touristic-place-attraction-max-people') + attraction.maxPeopleNumber}
                    />
                )}
            </div>
            <div className={styles.modify}>
                <GreyButton
                    className={styles.modifyButton}
                    type="button"
                    onClick={() =>
                        navigate('/tourism/modify-attraction-offer', {
                            state: { attractionId: attraction.attractionId }
                        })
                    }
                    text={t('tourism.touristic-place-stay-offer-modify')}
                />
            </div>
        </div>
    )
}

export default TourismAttractionOfferItem
