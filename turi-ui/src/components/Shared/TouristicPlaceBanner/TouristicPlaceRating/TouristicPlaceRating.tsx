import Rating from '../../Controls/Rating'
import TextExtraLight from '../../Controls/Text/TextExtraLight'
import styles from './TouristicPlaceRating.module.css'

interface Props {
    touristicPlaceId?: number
    stayName?: string
}

const TouristicPlaceRating = ({ touristicPlaceId, stayName }: Props) => {
    return (
        <div className={styles.rating}>
            {touristicPlaceId && !stayName && <Rating touristicPlaceId={touristicPlaceId} />}
            {stayName && <TextExtraLight text={stayName} />}
        </div>
    )
}

export default TouristicPlaceRating
