import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { useAuth } from '../../../hooks/app/useAuth.ts'
import { GreenButton } from '../../../components/Shared/Controls/Button'
import { useEffect, useState } from 'react'
import PremiumSection from '../../../components/Premium/PremiumSection'
import PremiumButtons from '../../../components/Premium/PremiumButtons'
import PremiumOfferComp from '../../../components/Premium/PremiumOfferComp'
import PremiumDescription from '../../../components/Premium/PremiumDescription'
import { PremiumOffer } from '../../../types'
import { premiumService } from '../../../services/premiumService.ts'

const PremiumPage = () => {
    const { t, navigate } = useHooks()
    const [offer, setOffer] = useState<PremiumOffer | null>(null)

    useAuth('/')

    useEffect(() => {
        const fetchOffer = async () => {
            const response = await premiumService.getOffer()
            if (response.status === 200) {
                const offer: PremiumOffer = await response.json()
                setOffer(offer)
            }
        }
        fetchOffer().catch((error) => error)
    }, [])

    return (
        <>
            <PremiumDescription text={t('premium.offer-description')} />
            <PremiumSection
                leftPanel={
                    <PremiumOfferComp
                        text={t('premium.offer-list-title')}
                        list={true}
                    />
                }
                rightPanel={
                    <PremiumOfferComp
                        text={t('premium.offer-access-price-title')}
                        list={false}
                        length={offer?.length}
                        price={offer?.price}
                    />
                }
            />
            <PremiumButtons
                rightButton={
                    <GreenButton
                        text={t('premium.offer-button')}
                        onClick={() => navigate('/premium/verify')}
                    />
                }
            />
        </>
    )
}

export default PremiumPage
