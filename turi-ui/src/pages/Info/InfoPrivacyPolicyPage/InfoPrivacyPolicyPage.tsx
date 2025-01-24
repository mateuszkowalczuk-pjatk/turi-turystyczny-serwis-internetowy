import { useHooks } from '../../../hooks/shared/useHooks.ts'
import PageContent from '../../../components/Shared/Contents/PageContent'
import PageTitle from '../../../components/Shared/PageTitle'
import InfoPanel from '../../../components/Info/InfoPanel'
import InfoTitle from '../../../components/Info/InfoTitle'
import InfoText from '../../../components/Info/InfoText'

const InfoPrivacyPolicyPage = () => {
    const { t } = useHooks()

    return (
        <PageContent
            title={<PageTitle text={t('info.privacy-police')} />}
            firstPanel={
                <InfoPanel
                    firstTitle={<InfoTitle text={t('info.privacy-police-data-title')} />}
                    firstText={<InfoText text={t('info.privacy-police-data')} />}
                    secondTitle={<InfoTitle text={t('info.privacy-police-rights-title')} />}
                    secondText={<InfoText text={t('info.privacy-police-rights')} />}
                    thirdTitle={<InfoTitle text={t('info.privacy-police-security-title')} />}
                    thirdText={<InfoText text={t('info.privacy-police-security')} />}
                    fourthTitle={<InfoTitle text={t('info.privacy-police-contact-title')} />}
                    fourthText={<InfoText text={t('info.privacy-police-contact')} />}
                />
            }
        />
    )
}

export default InfoPrivacyPolicyPage
