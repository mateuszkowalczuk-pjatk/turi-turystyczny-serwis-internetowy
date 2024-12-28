import { useTranslation } from 'react-i18next'
import MainInformationPanel from '../MainInformationPanel'
import MainInformationContent from '../MainInformationContent'
import MainInformationTitle from '../MainInformationTitle'
import MainInformationReservations from '../MainInformationReservations'

const UserMainInformation = () => {
    const { t } = useTranslation()

    return (
        <MainInformationPanel
            content={
                <MainInformationContent
                    title={<MainInformationTitle text={t('home.information.reservations')} />}
                    content={<MainInformationReservations />}
                />
            }
        />
    )
}

export default UserMainInformation
