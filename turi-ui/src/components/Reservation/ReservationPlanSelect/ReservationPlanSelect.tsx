import { useHooks } from '../../../hooks/shared/useHooks.ts'
import ReservationPlanSelectPart from '../ReservationPlanSelectPart'
import { Attraction } from '../../../types/attraction.ts'
import { TouristicPlace } from '../../../types/touristicPlace.ts'
import { ReservationAttraction } from '../../../types/reservation.ts'
import styles from './ReservationPlanSelect.module.css'

interface Props {
    reservationId: number
    reservationAttractions: ReservationAttraction[]
    attractions: Attraction[]
    setReservationAttractions: (
        value: ((prevState: ReservationAttraction[]) => ReservationAttraction[]) | ReservationAttraction[]
    ) => void
    touristicPlace?: TouristicPlace
    dateFrom: Date
    dateTo: Date
}

const ReservationPlanSelect = ({
    reservationId,
    reservationAttractions,
    attractions,
    setReservationAttractions,
    touristicPlace,
    dateFrom,
    dateTo
}: Props) => {
    const { t } = useHooks()

    return (
        <div className={styles.select}>
            <ReservationPlanSelectPart
                text={t('reservation.reservation-plan')}
                reservationAttractions={reservationAttractions}
                setReservationAttractions={setReservationAttractions}
                touristicPlace={touristicPlace}
                dateFrom={dateFrom}
                dateTo={dateTo}
            />
            <ReservationPlanSelectPart
                text={t('reservation.reservation-plan-attractions-title')}
                attractions={attractions}
                reservationId={reservationId}
                setReservationAttractions={setReservationAttractions}
                dateFrom={dateFrom}
                dateTo={dateTo}
            />
        </div>
    )
}

export default ReservationPlanSelect
