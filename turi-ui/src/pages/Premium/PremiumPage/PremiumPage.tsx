import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useTranslation } from 'react-i18next'
import PremiumDescription from '../../../components/Premium/PremiumDescription'
import PremiumSection from '../../../components/Premium/PremiumSection'
import PremiumButtons from '../../../components/Premium/PremiumButtons'
import PremiumOffer from '../../../components/Premium/PremiumOffer'
import { premiumService } from '../../../services/premiumService.ts'
import { Offer } from '../../../types'
import { useAuth } from '../../../hooks/useAuth.ts'
import { useSelector } from 'react-redux'
import { RootState } from '../../../store/store.ts'
import { GreenButton } from '../../../components/Shared/Controls/Button'

const PremiumPage = () => {
    const { t } = useTranslation()
    const navigate = useNavigate()
    const isPremiumAccount = useSelector((state: RootState) => state.premium.isPremiumAccount)
    const [offer, setOffer] = useState<Offer | null>(null)

    useAuth('/')

    useEffect(() => {
        // if (isPremiumAccount) {
        //     navigate('/')
        // }

        const fetchOffer = async () => {
            const response = await premiumService.getOffer()
            if (response.status === 200) {
                const offer: Offer = await response.json()
                setOffer(offer)
            }
        }
        fetchOffer().catch((error) => error)
    }, [isPremiumAccount])

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
