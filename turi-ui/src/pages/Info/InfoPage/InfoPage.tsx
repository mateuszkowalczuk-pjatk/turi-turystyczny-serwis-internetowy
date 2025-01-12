import { useTranslation } from 'react-i18next'
import Content from '../../../components/Shared/Content'
import PageTitle from '../../../components/Shared/PageTitle'
import InfoPanel from '../../../components/Info/InfoPanel/InfoPanel.tsx'

const InfoPage = () => {
    const { t } = useTranslation()

    return (
        <Content
            title={<PageTitle text={t('info.about-turi')} />}
            firstPanel={
                <InfoPanel

                />
                // null
            }
        />
    )
}

export default InfoPage
