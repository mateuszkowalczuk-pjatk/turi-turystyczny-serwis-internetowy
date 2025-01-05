import { useEffect, useState } from 'react'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { useTranslation } from 'react-i18next'
import TextRegular from '../Text/TextRegular'
import { faStar } from '@fortawesome/free-solid-svg-icons'
import styles from './Rating.module.css'

const Rating = ({ touristicPlaceId }: { touristicPlaceId: number | undefined }) => {
    const { t } = useTranslation()
    const [rating, setRating] = useState<number | null>(null)

    useEffect(() => {
        const fetchRating = async () => {
            if (touristicPlaceId) setRating(null)
        }
        fetchRating().catch((error) => error)
    }, [touristicPlaceId])

    return (
        <div className={styles.rating}>
            {rating && <TextRegular text={t('offer.rating') + rating} />}
            {!rating && <TextRegular text={t('offer.rating-not-found')} />}
            <FontAwesomeIcon
                icon={faStar}
                className={styles.icon}
            />
        </div>
    )
}

export default Rating
