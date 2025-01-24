import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { useEffect, useState } from 'react'
import { handleDateDisplay } from '../../../utils/handleDateTimeDisplay.ts'
import TextMedium from '../../Shared/Controls/Text/TextMedium'
import TextRegular from '../../Shared/Controls/Text/TextRegular'
import ProfileButtons from '../ProfileButtons'
import { Premium, PremiumOffer } from '../../../types'
import { premiumService } from '../../../services/premiumService.ts'
import styles from './ProfilePremium.module.css'

const ProfilePremium = () => {
    const { t } = useHooks()
    const [date, setDate] = useState<string>('')
    const [months, setMonths] = useState<number | null>(null)

    useEffect(() => {
        const fetchOffer = async () => {
            const response = await premiumService.getOffer()
            if (response.status === 200) {
                const offer: PremiumOffer = await response.json()
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
                    const finalDate = `${handleDateDisplay(start.toString() || '')} - ${handleDateDisplay(end.toString() || '')}`
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
