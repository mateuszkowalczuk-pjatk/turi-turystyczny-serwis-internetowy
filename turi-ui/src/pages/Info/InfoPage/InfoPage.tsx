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
                    firstTitle={<InfoTitle text={t('First title')} />}
                    firstText={<InfoText text={t('First text')} />}
                    secondTitle={<InfoTitle text={t('Second title')} />}
                    secondText={<InfoText text={t('Second text')} />}
                    thirdTitle={<InfoTitle text={t('third title')} />}
                    thirdText={<InfoText text={t('third text')} />}
                    fourthTitle={<InfoTitle text={t('Fourth title')} />}
                    fourthText={<InfoText text={t('Fourth text')} />}
                />
            }
        />
    )
}

export default InfoPage
