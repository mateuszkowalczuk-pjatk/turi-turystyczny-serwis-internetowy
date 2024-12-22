import { useTranslation } from 'react-i18next'
import PremiumDescription from '../../../components/Premium/PremiumDescription'
import PremiumSummary from '../../../components/Premium/PremiumSummary'
import { useEffect } from 'react'
import { useAuth } from '../../../hooks/useAuth.ts'
import { useDispatch, useSelector } from 'react-redux'
import { RootState } from '../../../store/store.ts'
import { authService } from '../../../services/authService.ts'
import { login } from '../../../store/slices/auth.ts'
import { accountService } from '../../../services/accountService.ts'
import { premiumAccount } from '../../../store/slices/premium.ts'

const PremiumSummaryPage = () => {
    const { t } = useTranslation()
    const isPremiumAccount = useSelector((state: RootState) => state.premium.isPremiumAccount)
    const dispatch = useDispatch()

    useAuth('/')

    useEffect(() => {
        // if (!isPremiumAccount) {
        //     navigate('/')
        // }

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
    }, [isPremiumAccount, dispatch])

    return (
        <>
            <PremiumDescription text={t('premium.summary-description')} />
            <PremiumSummary />
        </>
    )
}

export default PremiumSummaryPage
