import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { GreyButton } from '../../Shared/Controls/Button'
import { useEffect, useState } from 'react'
import ImagePanel from '../../Shared/Image/ImagePanel'
import TextRegular from '../../Shared/Controls/Text/TextRegular'
import { Stay } from '../../../types/stay.ts'
import { Image } from '../../../types/image.ts'
import { imageService } from '../../../services/imageService.ts'
import styles from './TourismStayOfferItem.module.css'

interface Props {
    stay: Stay
    index: number
    reservation?: boolean
    dateFrom?: string | null
    dateTo?: string | null
}

const TourismStayOfferItem = ({ stay, index, reservation, dateFrom, dateTo }: Props) => {
    const { t, navigate } = useHooks()
    const [image, setImage] = useState<Image>()

    useEffect(() => {
        const fetchImage = async () => {
            if (stay.stayId) {
                const imagesResponse = await imageService.getAllByStayId(stay.stayId)
                if (imagesResponse.status === 200) {
                    const imagesData = await imagesResponse.json()
                    setImage(imagesData[0])
                }
            }
        }

        fetchImage().catch((error) => error)
    }, [])

    const handleNavigate = () => {
        if (stay && dateFrom && dateTo) {
            navigate('/reservation', {
                state: {
                    stay: stay,
                    dateFrom: dateFrom,
                    dateTo: dateTo,
                    url: `${window.location.pathname}${window.location.search}`
                }
            })
        }
    }

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
                <TextRegular text={stay.name} />
                <TextRegular text={stay.description} />
            </div>
            <div className={styles.details}>
                <TextRegular
                    text={
                        t('tourism.touristic-place-stay-offer-price-text') +
                        stay.price +
                        t('tourism.touristic-place-stay-offer-currency')
                    }
                />
                <TextRegular text={t('tourism.touristic-place-stay-offer-people') + stay.peopleNumber} />
            </div>
            <div className={styles.modify}>
                {reservation && (
                    <GreyButton
                        className={styles.modifyButton}
                        type="button"
                        onClick={handleNavigate}
                        text={t('offer.reservation')}
                    />
                )}
                {!reservation && (
                    <GreyButton
                        className={styles.modifyButton}
                        type="button"
                        onClick={() => navigate('/tourism/modify-stay-offer', { state: { stayId: stay.stayId } })}
                        text={t('tourism.touristic-place-stay-offer-modify')}
                    />
                )}
            </div>
        </div>
    )
}

export default TourismStayOfferItem
