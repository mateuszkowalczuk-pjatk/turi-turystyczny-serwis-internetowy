import { useRedirectSome } from '../../../hooks/useRedirect.ts'
import { useLocation } from 'react-router-dom'
import { useStates } from '../../../hooks/useStates.ts'
import { useHooks } from '../../../hooks/useHooks.ts'
import PageContent from '../../../components/Shared/Contents/PageContent'
import PageReturn from '../../../components/Shared/PageReturn'
import TourismPanel from '../../../components/Tourism/TourismPanel'
import TourismHeader from '../../../components/Tourism/TourismHeader'
import TourismAttractionOfferPanel from '../../../components/Tourism/TourismAttractionOfferPanel'

const TourismAttractionOfferPage = ({ modify }: { modify?: boolean }) => {
    const { t } = useHooks()
    const { isAuthenticated, isPremium } = useStates()
    const { touristicPlaceId = null } = useLocation().state || {}

    useRedirectSome([!isAuthenticated, !isPremium], '/')

    return (
        <PageContent
            title={<PageReturn text={t('tourism.touristic-place-return')} />}
            firstPanel={
                <TourismPanel
                    header={<TourismHeader text={t('tourism.touristic-place-attraction-offer-title')} />}
                    content={
                        <TourismAttractionOfferPanel
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

export default TourismAttractionOfferPage
