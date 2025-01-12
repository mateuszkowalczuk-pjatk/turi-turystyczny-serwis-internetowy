import { useTranslation } from 'react-i18next'
import PageContent from '../../../components/Shared/Contents/PageContent'
import PageTitle from '../../../components/Shared/PageTitle'
import InfoPanel from '../../../components/Info/InfoPanel/InfoPanel.tsx'

const InfoPage = () => {
    const { t } = useTranslation()

    return (
        <PageContent
            title={<PageTitle text={t('info.about-turi')} />}
            firstPanel={<InfoPanel />}
        />
    )
}

export default InfoPage
