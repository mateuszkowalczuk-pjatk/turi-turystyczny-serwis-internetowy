import { useTranslation } from 'react-i18next'
import TourismContent from '../../../components/Tourism/TourismContent'
import Return from '../../../components/Shared/Return'
import TourismPanel from '../../../components/Tourism/TourismPanel'
import TourismHeader from '../../../components/Tourism/TourismHeader'
import TourismCurrentReservations from '../../../components/Tourism/TourismCurrentReservations'

const TourismStatisticsPage = () => {
    const { t } = useTranslation()

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
