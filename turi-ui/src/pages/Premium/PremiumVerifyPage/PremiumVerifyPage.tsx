import { useTranslation } from 'react-i18next'
import PremiumDescription from '../../../components/Premium/PremiumDescription'
import PremiumSection from '../../../components/Premium/PremiumSection'

const PremiumVerifyPage = () => {
    const { t } = useTranslation()

    return (
        <>
            <PremiumDescription text={t('premium.verify-description')} />
            <PremiumSection />
        </>
    )
}

export default PremiumVerifyPage
