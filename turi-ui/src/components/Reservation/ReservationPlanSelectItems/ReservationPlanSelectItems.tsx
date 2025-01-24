import { useHooks } from '../../../hooks/shared/useHooks.ts'
import ReservationPlanDate from '../ReservationPlanDate'
import ReservationPlanAttraction from '../ReservationPlanAttraction'
import ReservationPlanReservationAttraction from '../ReservationPlanReservationAttraction'
import { Attraction } from '../../../types/attraction.ts'
import { TouristicPlace } from '../../../types/touristicPlace.ts'
import { ReservationAttraction } from '../../../types/reservation.ts'
import styles from './ReservationPlanSelectItems.module.css'

interface Props {
    reservationAttractions?: ReservationAttraction[]
    attractions?: Attraction[]
    reservationId?: number
    setReservationAttractions?: (
        value: ((prevState: ReservationAttraction[]) => ReservationAttraction[]) | ReservationAttraction[]
    ) => void
    touristicPlace?: TouristicPlace
    dateFrom: Date
    dateTo: Date
}

const ReservationPlanSelectItems = ({
    reservationAttractions,
    attractions,
    reservationId,
    setReservationAttractions,
    touristicPlace,
    dateFrom,
    dateTo
}: Props) => {
    const { t } = useHooks()

    return (
        <div className={styles.items}>
            {touristicPlace && touristicPlace.checkInTimeFrom && touristicPlace.checkInTimeTo && (
                <ReservationPlanDate
                    text={t('reservation.reservation-check-in')}
                    date={dateFrom}
                    hourFrom={touristicPlace.checkInTimeFrom}
                    hourTo={touristicPlace.checkInTimeTo}
                />
            )}
            {reservationAttractions &&
                setReservationAttractions &&
                reservationAttractions.map((reservationAttraction) => (
                    <ReservationPlanReservationAttraction
                        key={reservationAttraction.reservationAttractionId}
                        reservationAttraction={reservationAttraction}
                        setReservationAttractions={setReservationAttractions}
                        plan
                    />
                ))}
            {touristicPlace && touristicPlace.checkOutTimeFrom && touristicPlace.checkOutTimeTo && (
                <ReservationPlanDate
                    text={t('reservation.reservation-check-out')}
                    date={dateTo}
                    hourFrom={touristicPlace.checkOutTimeFrom}
                    hourTo={touristicPlace.checkOutTimeTo}
                />
            )}
            {attractions &&
                reservationId &&
                setReservationAttractions &&
                dateFrom &&
                dateTo &&
                attractions.map(
                    (attraction) =>
                        attraction.attractionId && (
                            <ReservationPlanAttraction
                                key={attraction.attractionId}
                                reservationId={reservationId}
                                attractionId={attraction.attractionId}
                                attraction={attraction}
                                setReservationAttractions={setReservationAttractions}
                                dateFrom={dateFrom}
                                dateTo={dateTo}
                            />
                        )
                )}
        </div>
    )
}

export default ReservationPlanSelectItems
