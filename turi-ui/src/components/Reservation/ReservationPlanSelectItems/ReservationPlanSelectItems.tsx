import ReservationPlanAttraction from '../ReservationPlanAttraction'
import ReservationPlanReservationAttraction from '../ReservationPlanReservationAttraction'
import { Attraction } from '../../../types/attraction.ts'
import { ReservationAttraction } from '../../../types/reservation.ts'
import styles from './ReservationPlanSelectItems.module.css'

interface Props {
    reservationAttractions?: ReservationAttraction[]
    attractions?: Attraction[]
    reservationId?: number
    setReservationAttractions?: (
        value: ((prevState: ReservationAttraction[]) => ReservationAttraction[]) | ReservationAttraction[]
    ) => void
    dateFrom?: Date
    dateTo?: Date
}

const ReservationPlanSelectItems = ({
    reservationAttractions,
    attractions,
    reservationId,
    setReservationAttractions,
    dateFrom,
    dateTo
}: Props) => {
    return (
        <div className={styles.items}>
            {reservationAttractions &&
                setReservationAttractions &&
                reservationAttractions.map((reservationAttraction) => (
                    <ReservationPlanReservationAttraction
                        key={reservationAttraction.reservationAttractionId}
                        reservationAttraction={reservationAttraction}
                        setReservationAttractions={setReservationAttractions}
                    />
                ))}
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
