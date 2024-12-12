import { useTranslation } from 'react-i18next'
import PremiumDescription from '../../../components/Premium/PremiumDescription'
import PremiumSection from '../../../components/Premium/PremiumSection'

const PremiumPaymentPage = () => {
    const { t } = useTranslation()

    return (
        <>
            <PremiumDescription text={t('premium.payment-description')} />
            <PremiumSection />
        </>
    )
}

export default PremiumPaymentPage
