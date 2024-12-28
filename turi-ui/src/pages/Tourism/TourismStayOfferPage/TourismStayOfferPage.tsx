import { useAuthenticated } from '../../../store/slices/auth.ts'
import { useRedirectSome } from '../../../hooks/useRedirect.ts'
import { useTranslation } from 'react-i18next'
import { useLocation } from 'react-router-dom'
import { usePremium } from '../../../store/slices/premium.ts'
import TourismContent from '../../../components/Tourism/TourismContent'
import Return from '../../../components/Shared/Return'
import TourismPanel from '../../../components/Tourism/TourismPanel'
import TourismHeader from '../../../components/Tourism/TourismHeader'
import TourismStayOfferPanel from '../../../components/Tourism/TourismStayOfferPanel'

const TourismStayOfferPage = ({ modify }: { modify?: boolean }) => {
    const { t } = useTranslation()
    const isAuthenticated = useAuthenticated()
    const isPremium = usePremium()
    const touristicPlaceId = useLocation().state?.touristicPlaceId || null

    useRedirectSome([!isAuthenticated, !isPremium], '/')

    return (
        <TourismContent
            title={
                <Return
                    text={t('tourism.touristic-place-return')}
                    url={'/tourism'}
                />
            }
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
