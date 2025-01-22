import ReservationPlanReservationAttraction from '../ReservationPlanReservationAttraction'
import { ReservationAttraction } from '../../../types/reservation.ts'
import styles from './ReservationPlanItems.module.css'
import ReservationPlanDate from '../ReservationPlanDate'

const ReservationPlanItems = ({ reservationAttractions }: { reservationAttractions: ReservationAttraction[] }) => {
    return (
        <div className={styles.items}>
            <ReservationPlanDate key={0} />
            {reservationAttractions.map((reservationAttraction) => (
                <ReservationPlanReservationAttraction
                    key={reservationAttraction.reservationAttractionId}
                    reservationAttraction={reservationAttraction}
                />
            ))}
            <ReservationPlanDate key={1} />
        </div>
    )
}

export default ReservationPlanItems
