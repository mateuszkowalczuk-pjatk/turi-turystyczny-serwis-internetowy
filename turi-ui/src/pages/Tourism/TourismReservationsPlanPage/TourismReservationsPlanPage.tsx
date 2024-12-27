import { useTranslation } from 'react-i18next'
import TourismContent from '../../../components/Tourism/TourismContent'
import TourismPanel from '../../../components/Tourism/TourismPanel'
import TourismHeader from '../../../components/Tourism/TourismHeader'
import TourismCurrentReservations from '../../../components/Tourism/TourismCurrentReservations'
import Return from '../../../components/Shared/Return'

const TourismReservationsPlanPage = () => {
    const { t } = useTranslation()

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
                    header={<TourismHeader text={t('tourism.reservations-plan')} />}
                    content={<TourismCurrentReservations />}
                    size={'offer'}
                />
            }
        />
    )
}

export default TourismReservationsPlanPage
