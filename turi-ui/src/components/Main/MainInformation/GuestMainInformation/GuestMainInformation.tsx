import { useTranslation } from 'react-i18next'
import MainInformationPanel from '../MainInformationPanel'
import MainInformationContent from '../MainInformationContent'
import MainInformationTitle from '../MainInformationTitle'
import MainInformationText from '../MainInformationText'

const GuestMainInformation = () => {
    const { t } = useTranslation()

    return (
        <MainInformationPanel
            content={
                <MainInformationContent
                    title={<MainInformationTitle text={t('home.information.header-text')} />}
                    content={<MainInformationText text={t('home.information.sign-up-text')} />}
                    option={<MainInformationText text={t('home.information.premium-account-text')} />}
                />
            }
        />
    )
}

export default GuestMainInformation
