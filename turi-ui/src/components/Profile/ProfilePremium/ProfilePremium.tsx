import styles from './ProfilePremium.module.css'
import TextMedium from '../../Controls/Text/TextMedium'
import TextRegular from '../../Controls/Text/TextRegular'
import ProfileButtons from '../ProfileButtons'
import { useEffect, useState } from 'react'
import { premiumService } from '../../../services/premiumService.ts'
import { Offer, Premium } from '../../../types'
import { useTranslation } from 'react-i18next'

const ProfilePremium = () => {
    const [date, setDate] = useState<string>('')
    const [months, setMonths] = useState<number | null>(null)
    const { t } = useTranslation()

    useEffect(() => {
        const fetchOffer = async () => {
            const response = await premiumService.getOffer()
            if (response.status === 200) {
                const offer: Offer = await response.json()
                setMonths(offer.length)
            }
        }

        fetchOffer().catch((error) => error)

        const fetchDate = async () => {
            if (months !== null) {
                const response = await premiumService.getByAccount()
                if (response.status === 200) {
                    const premium: Premium = await response.json()
                    const start = premium.buyDate
                    const end = premium.expiresDate
                    const finalDate = `${start || ''} - ${end || ''}`
                    setDate(finalDate)
                }
            }
        }
        fetchDate().catch((error) => error)
    }, [months, date])

    return (
        <div className={styles.premium}>
            <TextMedium text={t('profile.premium-title')} />
            <TextRegular text={date} />
            <TextRegular text={t('profile.premium-description')} />
            <ProfileButtons />
        </div>
    )
}

export default ProfilePremium
