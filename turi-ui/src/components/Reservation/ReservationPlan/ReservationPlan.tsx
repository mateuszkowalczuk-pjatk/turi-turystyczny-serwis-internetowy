import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { GreenButton } from '../../Shared/Controls/Button'
import ReservationPlanItems from '../ReservationPlanItems'
import { ReservationAttraction } from '../../../types/reservation.ts'
import styles from './ReservationPlan.module.css'

interface Props {
    reservationId: number
    touristicPlaceId: number
    dateFrom: Date
    dateTo: Date
    reservationAttractions: ReservationAttraction[]
}

const ReservationPlan = ({ reservationId, touristicPlaceId, dateFrom, dateTo, reservationAttractions }: Props) => {
    const { t, navigate } = useHooks()

    return (
        <div className={styles.plan}>
            <GreenButton
                text={t('reservation.reservation-plan')}
                onClick={() =>
                    navigate('/reservation/plan', {
                        state: {
                            reservationId: reservationId,
                            touristicPlaceId: touristicPlaceId,
                            dateFrom: dateFrom,
                            dateTo: dateTo
                        }
                    })
                }
            />
            <ReservationPlanItems reservationAttractions={reservationAttractions} />
        </div>
    )
}

export default ReservationPlan
