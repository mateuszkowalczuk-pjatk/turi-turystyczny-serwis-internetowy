import { useHooks } from '../../../hooks/shared/useHooks.ts'
import PageContent from '../../../components/Shared/Contents/PageContent'
import PageTitle from '../../../components/Shared/PageTitle'
import InfoPanel from '../../../components/Info/InfoPanel'
import InfoTitle from '../../../components/Info/InfoTitle'
import InfoText from '../../../components/Info/InfoText'

const InfoTermsOfUsePage = () => {
    const { t } = useHooks()

    return (
        <PageContent
            title={<PageTitle text={t('info.terms-of-use')} />}
            firstPanel={
                <InfoPanel
                    firstTitle={<InfoTitle text={t('info.terms-of-use-profile-title')} />}
                    firstText={<InfoText text={t('info.terms-of-use-profile')} />}
                    secondTitle={<InfoTitle text={t('info.terms-of-use-reservation-title')} />}
                    secondText={<InfoText text={t('info.terms-of-use-reservation')} />}
                    thirdTitle={<InfoTitle text={t('info.terms-of-use-premium-title')} />}
                    thirdText={<InfoText text={t('info.terms-of-use-premium')} />}
                    fourthTitle={<InfoTitle text={t('info.terms-of-use-cookies-title')} />}
                    fourthText={<InfoText text={t('info.terms-of-use-cookies')} />}
                />
            }
        />
    )
}

export default InfoTermsOfUsePage
