import TouristicPlaceInformation from '../TouristicPlaceInformation'
import TouristicPlaceRating from '../TouristicPlaceRating'
import { TouristicPlace } from '../../../../types/touristicPlace.ts'
import { Address } from '../../../../types'
import styles from './TouristicPlaceBanner.module.css'

interface Props {
    touristicPlace: TouristicPlace
    address: Address | undefined
}

const TouristicPlaceBanner = ({ touristicPlace, address }: Props) => {
    return (
        <div className={styles.banner}>
            <TouristicPlaceInformation
                name={touristicPlace.name}
                touristicPlaceId={touristicPlace.touristicPlaceId}
                address={address}
            />
            <TouristicPlaceRating touristicPlaceId={touristicPlace.touristicPlaceId} />
        </div>
    )
}

export default TouristicPlaceBanner
