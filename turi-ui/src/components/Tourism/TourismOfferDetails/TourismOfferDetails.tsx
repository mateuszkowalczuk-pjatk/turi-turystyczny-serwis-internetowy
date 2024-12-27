import styles from './TourismOfferDetails.module.css'
import { ReactNode } from 'react'

interface Props {
    firstPanel: ReactNode
    secondPanel: ReactNode
    thirdPanel: ReactNode
}

const TourismOfferDetails = ({ firstPanel, secondPanel, thirdPanel }: Props) => {
    return (
        <div className={styles.details}>
            {firstPanel}
            {secondPanel}
            {thirdPanel}
        </div>
    )
}

export default TourismOfferDetails
