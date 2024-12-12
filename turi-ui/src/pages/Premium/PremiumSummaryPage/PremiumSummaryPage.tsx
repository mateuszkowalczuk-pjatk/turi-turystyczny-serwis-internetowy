import { useTranslation } from 'react-i18next'
import PremiumDescription from '../../../components/Premium/PremiumDescription'
import PremiumSummary from '../../../components/Premium/PremiumSummary'

const PremiumSummaryPage = () => {
    const { t } = useTranslation()

    // sprawdzenie czy są aktywowane stany auth i premium

    return (
        <>
            <PremiumDescription text={t('premium.summary-description')} />
            <PremiumSummary />
        </>
    )
}

export default PremiumSummaryPage
