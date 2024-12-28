import { useAuthenticated } from '../../../store/slices/auth.ts'
import { useRedirectSome } from '../../../hooks/useRedirect.ts'
import { useTranslation } from 'react-i18next'
import { usePremium } from '../../../store/slices/premium.ts'
import TourismContent from '../../../components/Tourism/TourismContent'
import Return from '../../../components/Shared/Return'
import TourismPanel from '../../../components/Tourism/TourismPanel'
import TourismHeader from '../../../components/Tourism/TourismHeader'
import TourismCurrentReservations from '../../../components/Tourism/TourismCurrentReservations'

const TourismStatisticsPage = () => {
    const { t } = useTranslation()
    const isAuthenticated = useAuthenticated()
    const isPremium = usePremium()

    useRedirectSome([!isAuthenticated, !isPremium], '/')

    return (
        <TourismContent
            title={
                <Return
                    text={t('tourism.home-return')}
                    url={'/'}
                />
            }
            firstPanel={
                <TourismPanel
                    header={<TourismHeader text={t('tourism.statistics-title')} />}
                    content={<TourismCurrentReservations />}
                    size={'offer'}
                />
            }
        />
    )
}

export default TourismStatisticsPage
