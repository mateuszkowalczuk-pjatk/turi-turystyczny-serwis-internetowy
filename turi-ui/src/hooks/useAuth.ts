import { useEffect } from 'react'
import { useHooks } from './useHooks.ts'
import { login, logout, useAuthenticated } from '../store/slices/auth.ts'
import { notPremiumAccount, premiumAccount } from '../store/slices/premium.ts'
import { accountService } from '../services/accountService.ts'
import { authService } from '../services/authService.ts'

export const useAuth = (path: string | null) => {
    const { dispatch, navigate } = useHooks()
    const isAuthenticated = useAuthenticated()

    useEffect(() => {
        const checkUserAuth = async () => {
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
        }

        checkUserAuth().catch((error) => error)
    }, [isAuthenticated, dispatch, navigate, path])
}
