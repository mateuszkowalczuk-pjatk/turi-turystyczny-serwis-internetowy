import InformationReservation from '../InformationReservation'
import styles from './InformationReservations.module.css'

const InformationReservations = () => {
    return (
        <div className={styles.reservations}>
            <InformationReservation />
        </div>
    )
}

export default InformationReservations
