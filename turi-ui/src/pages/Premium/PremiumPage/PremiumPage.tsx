import PremiumDescription from '../../../components/Premium/PremiumDescription'
import PremiumSection from '../../../components/Premium/PremiumSection'
import { useTranslation } from 'react-i18next'
import PremiumButtons from '../../../components/Premium/PremiumButtons'
import { GreenButton } from '../../../components/Controls/Button'
import PremiumOffer from '../../../components/Premium/PremiumOffer'
import { useEffect, useState } from 'react'
import { premiumService } from '../../../services/premiumService.ts'
import { useNavigate } from 'react-router-dom'

const PremiumPage = () => {
    const { t } = useTranslation()
    const navigate = useNavigate()
    const [offer, setOffer] = useState<Offer | null>(null)

    useEffect(() => {
        const fetchOffer = async () => {
            const data = await premiumService.getOffer()
            setOffer(data)
        }
        fetchOffer().then((error) => console.log(error))
    }, [t])

    const navigateToVerify = () => {
        navigate('/premium/verify')
    }

    return (
        <>
            <PremiumDescription text={t('premium.offer-description')} />
            <PremiumSection
                leftPanel={
                    <PremiumOffer
                        text={t('premium.offer-list-title')}
                        list={true}
                    />
                }
                rightPanel={
                    <PremiumOffer
                        text={t('premium.offer-access-price-title')}
                        list={false}
                        months={offer?.months}
                        price={offer?.price}
                    />
                }
            />
            <PremiumButtons
                rightButton={
                    <GreenButton
                        text={t('premium.offer-button')}
                        onClick={navigateToVerify}
                    />
                }
            />
        </>
    )
}

export default PremiumPage
