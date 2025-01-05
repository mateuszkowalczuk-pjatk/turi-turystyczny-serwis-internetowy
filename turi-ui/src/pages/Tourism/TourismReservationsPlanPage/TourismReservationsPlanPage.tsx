import { useAuthenticated } from '../../../store/slices/auth.ts'
import { useRedirectSome } from '../../../hooks/useRedirect.ts'
import { useTranslation } from 'react-i18next'
import { usePremium } from '../../../store/slices/premium.ts'
import Content from '../../../components/Shared/Content'
import Return from '../../../components/Shared/Return'
import TourismPanel from '../../../components/Tourism/TourismPanel'
import TourismHeader from '../../../components/Tourism/TourismHeader'
import TourismCurrentReservations from '../../../components/Tourism/TourismCurrentReservations'

const TourismReservationsPlanPage = () => {
    const { t } = useTranslation()
    const isAuthenticated = useAuthenticated()
    const isPremium = usePremium()

    useRedirectSome([!isAuthenticated, !isPremium], '/')

    return (
        <Content
            title={
                <Return
                    text={t('tourism.touristic-place-return')}
                    url={'/tourism'}
                />
            }
            firstPanel={
                <TourismPanel
                    header={<TourismHeader text={t('tourism.reservations-plan')} />}
                    content={<TourismCurrentReservations />}
                    size={'offer'}
                />
            }
        />
    )
}

export default TourismReservationsPlanPage
