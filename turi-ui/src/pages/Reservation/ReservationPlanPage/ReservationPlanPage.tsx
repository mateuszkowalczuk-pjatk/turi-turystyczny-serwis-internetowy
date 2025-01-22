import React from 'react'
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
    const { reservationId = null, touristicPlaceId = null, dateFrom = null, dateTo = null } = useLocation().state || {}
    const { reservationAttractions, setReservationAttractions, attractions } = useReservationPlan(
        reservationId,
        touristicPlaceId,
        dateFrom,
        dateTo
    )

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault()
    }

    return (
        <Loader>
            <PageContent
                title={<PageReturn text={t('reservation.reservation-plan-return')} />}
                firstPanel={
                    <ReservationPanel
                        onSubmit={handleSubmit}
                        step={1}
                        reservationPlanSelect={
                            <ReservationPlanSelect
                                reservationId={reservationId}
                                reservationAttractions={reservationAttractions}
                                attractions={attractions}
                                setReservationAttractions={setReservationAttractions}
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
