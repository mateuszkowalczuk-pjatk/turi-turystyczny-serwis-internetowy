import { Route, Routes } from 'react-router-dom'
import MainLayout from '../layouts/Main'
import HomePage from '../pages/Home'
import LoginRoutes from './LoginRoutes'
import SignUpRoutes from './SignUpRoutes'
import ProfileRoutes from './ProfileRoutes'
import PremiumRoutes from './PremiumRoutes'
import NotFoundPage from '../pages/NotFound'
import { useDispatch, useSelector } from 'react-redux'
import { useEffect } from 'react'
import { authService } from '../services/authService.ts'
import { login, logout } from '../store/slices/auth.ts'
import { RootState } from '../store/store.ts'
import { accountService } from '../services/accountService.ts'
import { notPremiumAccount, premiumAccount } from '../store/slices/premium.ts'
import TourismRoutes from './TourismRoutes.tsx'

const AppRoutes = () => {
    const isAuthenticated = useSelector((state: RootState) => state.auth.isAuthenticated)
    const dispatch = useDispatch()

    useEffect(() => {
        const checkUserAuth = async () => {
            if (!isAuthenticated) {
                const authorize = await authService.authorize()
                if (authorize.status === 200) {
                    dispatch(login())
                    const account = await accountService.isPremium()
                    if (account.status === 200 && (await account.json())) {
                        dispatch(premiumAccount())
                    }
                } else {
                    const refresh = await authService.refresh()
                    if (refresh.status === 200) {
                        dispatch(login())
                        const account = await accountService.isPremium()
                        console.log(account.json())
                        if (account.status === 200 && (await account.json())) {
                            dispatch(premiumAccount())
                        }
                    } else {
                        dispatch(logout())
                        dispatch(notPremiumAccount())
                    }
                }
            }
        }

        checkUserAuth().catch((error) => error)
    }, [dispatch, isAuthenticated])

    return (
        <Routes>
            <Route
                path="/"
                element={<MainLayout />}
            >
                <Route
                    index
                    element={<HomePage />}
                />
            </Route>
            <Route
                path="login/*"
                element={<LoginRoutes />}
            />
            <Route
                path="signup/*"
                element={<SignUpRoutes />}
            />
            <Route
                path="profile/*"
                element={<ProfileRoutes />}
            />
            <Route
                path="premium/*"
                element={<PremiumRoutes />}
            />
            <Route
                path="tourism/*"
                element={<TourismRoutes />}
            />
            <Route
                path="*"
                element={<NotFoundPage />}
            />
        </Routes>
    )
}

export default AppRoutes
