import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { useStates } from '../../../hooks/shared/useStates.ts'
import { useRedirectEvery } from '../../../hooks/shared/useRedirect.ts'
import { useReservationPlan } from '../../../hooks/pages/useReservationPlan.ts'
import Loader from '../../../components/Shared/Loading/Loader'
import PageReturn from '../../../components/Shared/PageReturn'
import PageContent from '../../../components/Shared/Contents/PageContent'
import ReservationPanel from '../../../components/Reservation/ReservationPanel'
import ReservationPlanSelect from '../../../components/Reservation/ReservationPlanSelect'

const ReservationPlanPage = () => {
    const { t, location } = useHooks()
    const { isAuthenticated } = useStates()
    const { reservationId = null, touristicPlace = null, dateFrom = null, dateTo = null } = location.state || {}
    const { reservationAttractions, setReservationAttractions, attractions } = useReservationPlan(
        reservationId,
        touristicPlace.touristicPlaceId,
        dateFrom,
        dateTo
    )

    useRedirectEvery([!isAuthenticated], '/')

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
