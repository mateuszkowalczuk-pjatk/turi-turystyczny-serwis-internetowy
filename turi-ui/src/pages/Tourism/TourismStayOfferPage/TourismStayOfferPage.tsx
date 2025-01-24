import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { useStates } from '../../../hooks/shared/useStates.ts'
import { useRedirectSome } from '../../../hooks/shared/useRedirect.ts'
import PageContent from '../../../components/Shared/Contents/PageContent'
import PageReturn from '../../../components/Shared/PageReturn'
import TourismPanel from '../../../components/Tourism/TourismPanel'
import TourismHeader from '../../../components/Tourism/TourismHeader'
import TourismStayOfferPanel from '../../../components/Tourism/TourismStayOfferPanel'

const TourismStayOfferPage = ({ modify }: { modify?: boolean }) => {
    const { t, location } = useHooks()
    const { isAuthenticated, isPremium } = useStates()
    const { touristicPlaceId = null } = location.state || {}

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
