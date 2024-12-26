import styles from './TourismTouristicPlaceLabel.module.css'

const TourismTouristicPlaceLabel = ({ text }: { text: string }) => {
    return <div className={styles.label}>{text}</div>
}

export default TourismTouristicPlaceLabel
