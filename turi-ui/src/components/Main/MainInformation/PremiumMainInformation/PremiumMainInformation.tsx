import { useTranslation } from 'react-i18next'
import MainInformationPanel from '../MainInformationPanel'
import MainInformationContent from '../MainInformationContent'
import MainInformationTitle from '../MainInformationTitle'
import MainInformationReservations from '../MainInformationReservations'

const PremiumMainInformation = () => {
    const { t } = useTranslation()

    return (
        <MainInformationPanel
            content={
                <MainInformationContent
                    title={<MainInformationTitle text={t('home.information.reservations-premium-title')} />}
                    content={<MainInformationReservations />}
                />
            }
        />
    )
}

export default PremiumMainInformation
