import { generateAddress } from '../../../../utils/generateAddress.ts'
import TouristicPlaceName from '../TouristicPlaceName'
import TextRegular from '../../Controls/Text/TextRegular'
import { Address } from '../../../../types'
import styles from './TouristicPlaceInformation.module.css'

interface Props {
    name: string | undefined
    touristicPlaceId: number | undefined
    address: Address | undefined
    isReservation?: boolean
}

const TouristicPlaceInformation = ({ name, touristicPlaceId, address, isReservation }: Props) => {
    return (
        <div className={styles.information}>
            <TouristicPlaceName
                name={name}
                touristicPlaceId={touristicPlaceId}
                isReservation={isReservation}
            />
            {address && <TextRegular text={generateAddress(address)} />}
        </div>
    )
}

export default TouristicPlaceInformation
