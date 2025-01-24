import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { useStates } from '../../../hooks/shared/useStates.ts'
import { useRedirectEvery } from '../../../hooks/shared/useRedirect.ts'
import PageContent from '../../../components/Shared/Contents/PageContent'
import PageTitle from '../../../components/Shared/PageTitle'
import ReservationReservations from '../../../components/Reservation/ReservationReservations'
import { ReservationStatus } from '../../../types/reservation.ts'

const ReservationRealizedPage = () => {
    const { t } = useHooks()
    const { isAuthenticated } = useStates()

    useRedirectEvery([!isAuthenticated], '/')

    return (
        <PageContent
            title={<PageTitle text={t('reservation.realized')} />}
            firstPanel={<ReservationReservations statuses={[ReservationStatus.REALIZED]} />}
        />
    )
}

export default ReservationRealizedPage
