import { useHooks } from '../../../hooks/shared/useHooks.ts'
import ReservationPlanSelectPart from '../ReservationPlanSelectPart'
import { Attraction } from '../../../types/attraction.ts'
import { ReservationAttraction } from '../../../types/reservation.ts'
import styles from './ReservationPlanSelect.module.css'

interface Props {
    reservationId: number
    reservationAttractions: ReservationAttraction[]
    attractions: Attraction[]
    setReservationAttractions: (
        value: ((prevState: ReservationAttraction[]) => ReservationAttraction[]) | ReservationAttraction[]
    ) => void
    dateFrom: Date
    dateTo: Date
}

const ReservationPlanSelect = ({
    reservationId,
    reservationAttractions,
    attractions,
    setReservationAttractions,
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
