import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { useLocation } from 'react-router-dom'
import { useReservationPlan } from '../../../hooks/pages/useReservationPlan.ts'
import Loader from '../../../components/Shared/Loading/Loader'
import PageContent from '../../../components/Shared/Contents/PageContent'
import PageReturn from '../../../components/Shared/PageReturn'
import ReservationPanel from '../../../components/Reservation/ReservationPanel'
import ReservationPlanSelect from '../../../components/Reservation/ReservationPlanSelect'

const ReservationPlanPage = () => {
    const { t } = useHooks()
    const { reservationId = null, touristicPlace = null, dateFrom = null, dateTo = null } = useLocation().state || {}
    const { reservationAttractions, setReservationAttractions, attractions } = useReservationPlan(
        reservationId,
        touristicPlace.touristicPlaceId,
        dateFrom,
        dateTo
    )

    return (
        <Loader>
            <PageContent
                title={<PageReturn text={t('reservation.reservation-plan-return')} />}
                firstPanel={
                    <ReservationPanel
                        step={1}
                        reservationPlanSelect={
                            <ReservationPlanSelect
                                reservationId={reservationId}
                                reservationAttractions={reservationAttractions}
                                attractions={attractions}
                                setReservationAttractions={setReservationAttractions}
                                touristicPlace={touristicPlace}
                                dateFrom={dateFrom}
                                dateTo={dateTo}
                            />
                        }
                        plan
                    />
                }
            />
        </Loader>
    )
}

export default ReservationPlanPage
