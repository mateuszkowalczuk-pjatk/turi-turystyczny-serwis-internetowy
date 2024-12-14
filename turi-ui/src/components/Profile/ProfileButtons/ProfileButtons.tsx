import styles from './ProfileButtons.module.css'
import { GreenButton } from '../../Controls/Button'
import { useTranslation } from 'react-i18next'
import { premiumService } from '../../../services/premiumService.ts'
import { PaymentMethod } from '../../../types'
import { useState } from 'react'
import { useNavigate } from 'react-router-dom'

const ProfileButtons = () => {
    const { t } = useTranslation()
    const [error, setError] = useState<string | null>(null)
    const navigate = useNavigate()

    const handlePremiumRenew = async () => {
        const response = await premiumService.renew(PaymentMethod.CARD)
        if (response.status === 200) window.location.href = await response.text()
        else setError(t('premium.premium-payment-default-error'))
    }

    const handlePremiumCancel = async () => {
        const response = await premiumService.cancel()
        if (response.status === 200) navigate('/')
        else setError(t('premium.premium-payment-default-error'))
    }

    return (
        <div className={styles.buttons}>
            {error && <div className={styles.error}>{error}</div>}
            <GreenButton
                text={t('profile.premium-renew-button')}
                onClick={handlePremiumRenew}
            />
            <GreenButton
                text={t('profile.premium-cancel-button')}
                onClick={handlePremiumCancel}
            />
        </div>
    )
}

export default ProfileButtons
