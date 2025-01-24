import TouristicPlaceRating from '../TouristicPlaceRating'
import TouristicPlaceInformation from '../TouristicPlaceInformation'
import { Address } from '../../../../types'
import { TouristicPlace } from '../../../../types/touristicPlace.ts'
import styles from './TouristicPlaceBanner.module.css'

interface Props {
    touristicPlace: TouristicPlace
    address: Address | undefined
    isReservation?: boolean
    stayName?: string
}

const TouristicPlaceBanner = ({ touristicPlace, address, isReservation, stayName }: Props) => {
    return (
        <div className={styles.banner}>
            <TouristicPlaceInformation
                name={touristicPlace.name}
                touristicPlaceId={touristicPlace.touristicPlaceId}
                address={address}
                isReservation={isReservation}
            />
            <TouristicPlaceRating
                touristicPlaceId={touristicPlace.touristicPlaceId}
                stayName={stayName}
            />
        </div>
    )
}

export default TouristicPlaceBanner
