import ReservationPlanReservationAttraction from '../ReservationPlanReservationAttraction'
import { ReservationAttraction } from '../../../types/reservation.ts'
import styles from './ReservationPlanItems.module.css'
import ReservationPlanDate from '../ReservationPlanDate'
import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { TouristicPlace } from '../../../types/touristicPlace.ts'

const ReservationPlanItems = ({
    reservationAttractions,
    touristicPlace,
    dateFrom,
    dateTo
}: {
    reservationAttractions: ReservationAttraction[]
    touristicPlace: TouristicPlace
    dateFrom: Date
    dateTo: Date
}) => {
    const { t } = useHooks()

    return (
        <div className={styles.items}>
            {touristicPlace.checkInTimeFrom && touristicPlace.checkInTimeTo && (
                <ReservationPlanDate
                    text={t('reservation.reservation-check-in')}
                    date={dateFrom}
                    hourFrom={touristicPlace.checkInTimeFrom}
                    hourTo={touristicPlace.checkInTimeTo}
                />
            )}
            {reservationAttractions.map((reservationAttraction) => (
                <ReservationPlanReservationAttraction
                    key={reservationAttraction.reservationAttractionId}
                    reservationAttraction={reservationAttraction}
                />
            ))}
            {touristicPlace.checkOutTimeFrom && touristicPlace.checkOutTimeTo && (
                <ReservationPlanDate
                    text={t('reservation.reservation-check-out')}
                    date={dateTo}
                    hourFrom={touristicPlace.checkOutTimeFrom}
                    hourTo={touristicPlace.checkOutTimeTo}
                />
            )}
        </div>
    )
}

export default ReservationPlanItems
