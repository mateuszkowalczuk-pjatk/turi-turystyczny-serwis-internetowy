import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { GreenButton } from '../../Shared/Controls/Button'
import ReservationPlanItems from '../ReservationPlanItems'
import { ReservationAttraction } from '../../../types/reservation.ts'
import styles from './ReservationPlan.module.css'
import { TouristicPlace } from '../../../types/touristicPlace.ts'

interface Props {
    reservationId: number
    touristicPlace: TouristicPlace
    dateFrom: Date
    dateTo: Date
    reservationAttractions: ReservationAttraction[]
    onlyDisplay?: boolean
}

const ReservationPlan = ({
    reservationId,
    touristicPlace,
    dateFrom,
    dateTo,
    reservationAttractions,
    onlyDisplay
}: Props) => {
    const { t, navigate } = useHooks()

    return (
        <div className={styles.plan}>
            {!onlyDisplay && (
                <GreenButton
                    text={t('reservation.reservation-plan')}
                    onClick={() =>
                        navigate('/reservation/plan', {
                            state: {
                                reservationId: reservationId,
                                touristicPlace: touristicPlace,
                                dateFrom: dateFrom,
                                dateTo: dateTo
                            }
                        })
                    }
                />
            )}
            {onlyDisplay && (
                <GreenButton
                    text={t('reservation.reservation-plan')}
                    type={'button'}
                />
            )}
            <ReservationPlanItems
                reservationAttractions={reservationAttractions}
                touristicPlace={touristicPlace}
                dateFrom={dateFrom}
                dateTo={dateTo}
            />
        </div>
    )
}

export default ReservationPlan
