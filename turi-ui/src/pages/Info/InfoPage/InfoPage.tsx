import { useHooks } from '../../../hooks/shared/useHooks.ts'
import PageContent from '../../../components/Shared/Contents/PageContent'
import PageTitle from '../../../components/Shared/PageTitle'
import InfoPanel from '../../../components/Info/InfoPanel'
import InfoTitle from '../../../components/Info/InfoTitle'
import InfoText from '../../../components/Info/InfoText'

const InfoPage = () => {
    const { t } = useHooks()

    return (
        <PageContent
            title={<PageTitle text={t('info.about-turi')} />}
            firstPanel={
                <InfoPanel
                    firstTitle={<InfoTitle text={t('info.about-turi-about-title')} />}
                    firstText={<InfoText text={t('info.about-turi-about')} />}
                    secondTitle={<InfoTitle text={t('info.about-turi-goal-title')} />}
                    secondText={<InfoText text={t('info.about-turi-goal')} />}
                    thirdTitle={<InfoTitle text={t('info.about-turi-users-title')} />}
                    thirdText={<InfoText text={t('info.about-turi-users')} />}
                    fourthTitle={<InfoTitle text={t('info.about-turi-functionality-title')} />}
                    fourthText={<InfoText text={t('info.about-turi-functionality')} />}
                />
            }
        />
    )
}

export default InfoPage
