import { useRedirectSome } from '../../../hooks/useRedirect.ts'
import { useTranslation } from 'react-i18next'
import { useLocation } from 'react-router-dom'
import { useStates } from '../../../hooks/useStates.ts'
import PageContent from '../../../components/Shared/Contents/PageContent'
import PageReturn from '../../../components/Shared/PageReturn'
import TourismPanel from '../../../components/Tourism/TourismPanel'
import TourismHeader from '../../../components/Tourism/TourismHeader'
import TourismStayOfferPanel from '../../../components/Tourism/TourismStayOfferPanel'

const TourismStayOfferPage = ({ modify }: { modify?: boolean }) => {
    const { t } = useTranslation()
    const { isAuthenticated, isPremium } = useStates()
    const { touristicPlaceId = null } = useLocation().state || {}

    useRedirectSome([!isAuthenticated, !isPremium], '/')

    return (
        <PageContent
            title={<PageReturn text={t('tourism.touristic-place-return')} />}
            firstPanel={
                <TourismPanel
                    header={<TourismHeader text={t('tourism.touristic-place-stay-offer-title')} />}
                    content={
                        <TourismStayOfferPanel
                            touristicPlaceId={touristicPlaceId}
                            modify={modify}
                        />
                    }
                    size={'offer'}
                />
            }
        />
    )
}

export default TourismStayOfferPage
