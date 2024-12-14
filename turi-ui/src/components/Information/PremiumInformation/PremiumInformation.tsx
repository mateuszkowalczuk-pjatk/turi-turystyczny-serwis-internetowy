import { useTranslation } from 'react-i18next'
import InformationPanel from '../InformationPanel'
import InformationContent from '../InformationContent'
import InformationTitle from '../InformationTitle'
import InformationReservations from '../InformationReservations'

const PremiumInformation = () => {
    const { t } = useTranslation()

    return (
        <InformationPanel
            content={
                <InformationContent
                    title={<InformationTitle text={t('home.information.reservations-premium-title')} />}
                    content={<InformationReservations />}
                />
            }
        />
    )
}

export default PremiumInformation
