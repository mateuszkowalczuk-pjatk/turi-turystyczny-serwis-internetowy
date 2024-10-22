import { useTranslation } from 'react-i18next'
import InformationPanel from '../InformationPanel'
import InformationContent from '../InformationContent'
import InformationTitle from '../InformationTitle'
import InformationText from '../InformationText'

const GuestInformation = () => {
    const { t } = useTranslation();

    return (
        <InformationPanel
            content={
                <InformationContent
                    title={
                        <InformationTitle
                            text={t('home.information.header-text')}
                        />
                    }
                    content={
                        <InformationText
                            text={t('home.information.sign-up-text')}
                        />
                    }
                    option={
                        <InformationText
                            text={t('home.information.premium-account-text')}
                        />
                    }
                />
            }
        />
    )
}

export default GuestInformation