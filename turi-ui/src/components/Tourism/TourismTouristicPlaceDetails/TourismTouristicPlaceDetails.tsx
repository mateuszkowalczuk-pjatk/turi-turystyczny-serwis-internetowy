import { ReactNode } from 'react'
import styles from './TourismTouristicPlaceDetails.module.css'

interface Props {
    firstPanel: ReactNode
    secondPanel: ReactNode
    thirdPanel: ReactNode
    fourthPanel: ReactNode
    fifthPanel: ReactNode
    sixthPanel: ReactNode
}

const TourismTouristicPlaceDetails = ({
    firstPanel,
    secondPanel,
    thirdPanel,
    fourthPanel,
    fifthPanel,
    sixthPanel
}: Props) => {
    return (
        <div className={styles.details}>
            {firstPanel}
            {secondPanel}
            {thirdPanel}
            {fourthPanel}
            {fifthPanel}
            {sixthPanel}
        </div>
    )
}

export default TourismTouristicPlaceDetails
