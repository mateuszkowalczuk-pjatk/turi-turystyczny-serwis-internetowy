import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { useStates } from '../../../hooks/shared/useStates.ts'
import { useRedirectEvery } from '../../../hooks/shared/useRedirect.ts'
import PageContent from '../../../components/Shared/Contents/PageContent'
import PageTitle from '../../../components/Shared/PageTitle'
import ReservationReservations from '../../../components/Reservation/ReservationReservations'
import { ReservationStatus } from '../../../types/reservation.ts'

const ReservationsPage = () => {
    const { t } = useHooks()
    const { isAuthenticated } = useStates()

    useRedirectEvery([!isAuthenticated], '/')

    return (
        <PageContent
            title={<PageTitle text={t('reservation.reservations')} />}
            firstPanel={
                <ReservationReservations statuses={[ReservationStatus.RESERVATION, ReservationStatus.REALIZATION]} />
            }
        />
    )
}

export default ReservationsPage
