import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { useState } from 'react'
import { GreenButton } from '../../Shared/Controls/Button'
import { PaymentMethod } from '../../../types'
import { premiumService } from '../../../services/premiumService.ts'
import { authService } from '../../../services/authService.ts'
import { notPremiumAccount } from '../../../store/slices/premium.ts'
import { logout } from '../../../store/slices/auth.ts'
import styles from './ProfileButtons.module.css'

const ProfileButtons = () => {
    const { t, navigate, dispatch } = useHooks()
    const [error, setError] = useState<string | null>(null)

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
