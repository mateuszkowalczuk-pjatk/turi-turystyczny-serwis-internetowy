import { useAuthenticated } from '../../../store/slices/auth.ts'
import { useRedirectSome } from '../../../hooks/useRedirect.ts'
import { useTranslation } from 'react-i18next'
import { usePremium } from '../../../store/slices/premium.ts'
import PageContent from '../../../components/Shared/Contents/PageContent'
import PageReturn from '../../../components/Shared/PageReturn'
import TourismPanel from '../../../components/Tourism/TourismPanel'
import TourismHeader from '../../../components/Tourism/TourismHeader'
import TourismCurrentReservations from '../../../components/Tourism/TourismCurrentReservations'

const TourismStaysPlanPage = () => {
    const { t } = useTranslation()
    const isAuthenticated = useAuthenticated()
    const isPremium = usePremium()

    useRedirectSome([!isAuthenticated, !isPremium], '/')

    return (
        <PageContent
            title={
                <PageReturn
                    text={t('tourism.touristic-place-return')}
                    url={'/tourism'}
                />
            }
            firstPanel={
                <TourismPanel
                    header={<TourismHeader text={t('tourism.stays-plan')} />}
                    content={<TourismCurrentReservations />}
                    size={'offer'}
                />
            }
        />
    )
}

export default TourismStaysPlanPage
