import styles from './ProfileButtons.module.css'
import { GreenButton } from '../../Controls/Button'
import { useTranslation } from 'react-i18next'
import { premiumService } from '../../../services/premiumService.ts'
import { PaymentMethod } from '../../../types'
import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useDispatch } from 'react-redux'
import { logout } from '../../../store/slices/auth.ts'
import { notPremiumAccount } from '../../../store/slices/premium.ts'
import { authService } from '../../../services/authService.ts'

const ProfileButtons = () => {
    const { t } = useTranslation()
    const [error, setError] = useState<string | null>(null)
    const navigate = useNavigate()
    const dispatch = useDispatch()

    const handlePremiumRenew = async () => {
        const response = await premiumService.renew(PaymentMethod.CARD)
        if (response.status === 200) window.location.href = await response.text()
        else setError(t('profile.premium-renew-default-error'))
    }

    const handlePremiumCancel = async () => {
        const response = await premiumService.cancel()
        if (response.status === 200) {
            const response = await authService.logout()
            if (response.status === 200) {
                dispatch(logout())
                dispatch(notPremiumAccount())
                navigate('/')
            }
        } else setError(t('profile.premium-cancel-default-error'))
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
