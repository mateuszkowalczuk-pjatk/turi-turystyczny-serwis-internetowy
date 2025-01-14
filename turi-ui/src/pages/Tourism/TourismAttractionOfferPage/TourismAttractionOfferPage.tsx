import { useAuthenticated } from '../../../store/slices/auth.ts'
import { useRedirectSome } from '../../../hooks/useRedirect.ts'
import { useTranslation } from 'react-i18next'
import { useLocation } from 'react-router-dom'
import { usePremium } from '../../../store/slices/premium.ts'
import Content from '../../../components/Shared/Content'
import Return from '../../../components/Shared/Return'
import TourismPanel from '../../../components/Tourism/TourismPanel'
import TourismHeader from '../../../components/Tourism/TourismHeader'
import TourismAttractionOfferPanel from '../../../components/Tourism/TourismAttractionOfferPanel'

const TourismAttractionOfferPage = ({ modify }: { modify?: boolean }) => {
    const { t } = useTranslation()
    const isAuthenticated = useAuthenticated()
    const isPremium = usePremium()
    const touristicPlaceId = useLocation().state?.touristicPlaceId || null

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
