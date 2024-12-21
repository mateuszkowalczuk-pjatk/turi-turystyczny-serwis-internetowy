import { useTranslation } from 'react-i18next'
import PremiumHeader from '../../components/Header/PremiumHeader'
import PageTitle from '../../components/Shared/PageTitle'
import PremiumFooter from '../../components/Footer/PremiumFooter'
import styles from '../Layout.module.css'

const TourismLayout = () => {
    const { t } = useTranslation()

    return (
        <div className={styles.layout}>
            <PremiumHeader />
            <PageTitle text={t('tourism.title')} />
            {/* <TourismCurrentReservation - aktualnie realizowane rezerwacje /> */}
            {/* <TourismUpcomingReservation - nadchodzące rezerwacje  /> */}
            {/* <TourismTouristicPlace /> */}
            {/* <TourismOffers - wszystkie oferty pobytu i atrakcji (z przełącznkami: wszystkie, pobyty, atrakcje) /> */}
            {/* <TourismReservations - wszystkie rezerwacje w kolejności realizowane -> nadchodzące -> zrealizowane /> */}
            <PremiumFooter />
        </div>
    )
}

export default TourismLayout
