import { useNavigate } from 'react-router-dom'
import { useDispatch, useSelector } from 'react-redux'
import { RootState } from '../store/store.ts'
import { useEffect } from 'react'
import { authService } from '../services/authService.ts'
import { login, logout } from '../store/slices/auth.ts'
import { accountService } from '../services/accountService.ts'
import { notPremiumAccount, premiumAccount } from '../store/slices/premium.ts'

export const useAuth = () => {
    const navigate = useNavigate()
    const isAuthenticated = useSelector((state: RootState) => state.auth.isAuthenticated)
    const dispatch = useDispatch()

    useEffect(() => {
        const checkUserAuth = async () => {
            if (!isAuthenticated) {
                const authorize = await authService.authorize()
                if (authorize.status === 200) {
                    dispatch(login())
                    const account = await accountService.isPremium()
                    if (account.status === 200) {
                        dispatch(premiumAccount())
                    }
                } else {
                    const refresh = await authService.refresh()
                    if (refresh.status === 200) {
                        dispatch(login())
                        const account = await accountService.isPremium()
                        if (account.status === 200) {
                            dispatch(premiumAccount())
                        }
                    } else {
                        dispatch(logout())
                        dispatch(notPremiumAccount())
                        navigate('/')
                    }
                }
            }
        }

        checkUserAuth().catch((error) => error)
    }, [dispatch, isAuthenticated])
}
