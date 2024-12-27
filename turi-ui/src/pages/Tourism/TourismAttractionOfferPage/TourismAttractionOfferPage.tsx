import { useTranslation } from 'react-i18next'
import { useLocation } from 'react-router-dom'
import TourismContent from '../../../components/Tourism/TourismContent'
import Return from '../../../components/Shared/Return'
import TourismPanel from '../../../components/Tourism/TourismPanel'
import TourismHeader from '../../../components/Tourism/TourismHeader'
import TourismAttractionOfferPanel from '../../../components/Tourism/TourismAttractionOfferPanel'

const TourismAttractionOfferPage = ({ modify }: { modify?: boolean }) => {
    const { t } = useTranslation()
    const { touristicPlaceId } = useLocation().state

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
