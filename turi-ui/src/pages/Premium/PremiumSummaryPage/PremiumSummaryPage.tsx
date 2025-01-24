import { useEffect } from 'react'
import { useAuth } from '../../../hooks/app/useAuth.ts'
import PremiumDescription from '../../../components/Premium/PremiumDescription'
import PremiumSummary from '../../../components/Premium/PremiumSummary'
import { premiumAccount } from '../../../store/slices/premium.ts'
import { login } from '../../../store/slices/auth.ts'
import { accountService } from '../../../services/accountService.ts'
import { authService } from '../../../services/authService.ts'
import { useHooks } from '../../../hooks/shared/useHooks.ts'

const PremiumSummaryPage = () => {
    const { t, dispatch } = useHooks()

    useAuth('/')

    useEffect(() => {
        const fetchOffer = async () => {
            const refresh = await authService.refresh()
            if (refresh.status === 200) {
                dispatch(login())
                const account = await accountService.isPremium()
                if (account.status === 200) {
                    dispatch(premiumAccount())
                }
            }
        }

        fetchOffer().catch((error) => error)
    }, [dispatch])

    return (
        <>
            <PremiumDescription text={t('premium.summary-description')} />
            <PremiumSummary />
        </>
    )
}

export default PremiumSummaryPage
