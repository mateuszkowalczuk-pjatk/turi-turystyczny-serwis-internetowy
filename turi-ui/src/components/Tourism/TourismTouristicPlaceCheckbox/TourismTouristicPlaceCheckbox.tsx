import { ReactNode } from 'react'
import styles from './TourismTouristicPlaceCheckbox.module.css'

interface Props {
    checkbox: ReactNode
}

const TourismTouristicPlaceCheckbox = ({ checkbox }: Props) => {
    return <div className={styles.checkbox}>{checkbox}</div>
}

export default TourismTouristicPlaceCheckbox
