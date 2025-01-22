import styles from './ReservationPlan.module.css'

const ReservationPlan = ({ reservationId }: { reservationId: number }) => {
    return (
        <div className={styles.plan}>
            {reservationId}
        </div>
    )
}

export default ReservationPlan
