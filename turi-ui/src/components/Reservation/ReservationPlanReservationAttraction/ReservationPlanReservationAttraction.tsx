import { ReservationAttraction } from '../../../types/reservation.ts'
import styles from './ReservationPlanReservationAttraction.module.css'

interface Props {
    reservationAttraction: ReservationAttraction
    setReservationAttractions?: (
        value: ((prevState: ReservationAttraction[]) => ReservationAttraction[]) | ReservationAttraction[]
    ) => void
}

const ReservationPlanReservationAttraction = ({ reservationAttraction, setReservationAttractions }: Props) => {
    // przycisk do usunięcia

    return <div className={styles.reservation}>{reservationAttraction.reservationId}</div>
}

export default ReservationPlanReservationAttraction
