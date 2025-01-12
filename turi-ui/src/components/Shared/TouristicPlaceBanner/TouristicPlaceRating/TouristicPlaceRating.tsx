import Rating from '../../Controls/Rating'
import styles from './TouristicPlaceRating.module.css'

const TouristicPlaceRating = ({ touristicPlaceId }: { touristicPlaceId: number | undefined }) => {
    return (
        <div className={styles.rating}>
            <Rating touristicPlaceId={touristicPlaceId} />
        </div>
    )
}

export default TouristicPlaceRating
