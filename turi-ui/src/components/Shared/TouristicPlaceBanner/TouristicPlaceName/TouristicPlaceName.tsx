import TextRegular from '../../Controls/Text/TextRegular'
import Favourite from '../../Controls/Favourite'
import styles from './TouristicPlaceName.module.css'

interface Props {
    name: string | undefined
    touristicPlaceId: number | undefined
    isReservation?: boolean
}

const TouristicPlaceName = ({ name, touristicPlaceId, isReservation }: Props) => {
    return (
        <div className={styles.name}>
            <TextRegular text={name || ''} />
            {isReservation && <Favourite touristicPlaceId={touristicPlaceId} />}
        </div>
    )
}

export default TouristicPlaceName
