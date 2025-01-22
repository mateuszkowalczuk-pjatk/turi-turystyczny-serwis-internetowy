import { ReservationAttraction } from '../../../types/reservation.ts'
import styles from './ReservationPlanItem.module.css'

const ReservationPlanItem = ({ reservationAttraction }: { reservationAttraction: ReservationAttraction }) => {
    return (
        <div className={styles.item}>
            {reservationAttraction.reservationId}
        </div>
    )
}

export default ReservationPlanItem
