import { useEffect } from 'react'
import { useStates } from './useStates.ts'
import { useHooks } from './useHooks.ts'
import { notPremiumAccount, premiumAccount } from '../store/slices/premium.ts'
import { startLoading, stopLoading } from '../store/slices/loading.ts'
import { accountService } from '../services/accountService.ts'
import { login, logout } from '../store/slices/auth.ts'
import { authService } from '../services/authService.ts'

export const useAuth = (path: string | null) => {
    const { dispatch, navigate } = useHooks()
    const { isAuthenticated } = useStates()

    useEffect(() => {
        const checkUserAuth = async () => {
            dispatch(startLoading())

            try {
                if (!isAuthenticated) {
                    const authorize = await authService.authorize()
                    if (authorize.status === 200) {
                        dispatch(login())
                        const account = await accountService.isPremium()
                        if (account.status === 200 && (await account.json())) dispatch(premiumAccount())
                    } else {
                        const refresh = await authService.refresh()
                        if (refresh.status === 200) {
                            dispatch(login())
                            const account = await accountService.isPremium()
                            if (account.status === 200 && (await account.json())) dispatch(premiumAccount())
                        } else {
                            dispatch(logout())
                            dispatch(notPremiumAccount())
                            if (path) navigate(path)
                        }
                    }
                }
            } finally {
                dispatch(stopLoading())
            }
        }

        checkUserAuth().catch((error) => error)
    }, [isAuthenticated, dispatch, navigate, path])
}
