import { Route, Routes } from 'react-router-dom'
import MainLayout from '../layouts/Main'
import HomePage from '../pages/Home'
import LoginRoutes from './LoginRoutes'
import SignUpRoutes from './SignUpRoutes'
import ProfileRoutes from './ProfileRoutes'
import NotFoundPage from '../pages/NotFound'
import { useDispatch, useSelector } from 'react-redux'
import { useEffect } from 'react'
import { authService } from '../services/authService.ts'
import { login } from '../store/slices/auth.ts'
import { RootState } from '../store/store.ts'

const AppRoutes = () => {
    const isAuthenticated = useSelector((state: RootState) => state.auth.isAuthenticated)
    const dispatch = useDispatch()

    useEffect(() => {
        const checkUserAuth = async () => {
            if (!isAuthenticated) {
                const authorize = await authService.authorize()
                if (authorize.status === 200) dispatch(login())
                else {
                    const refresh = await authService.refresh()
                    if (refresh.status === 200) dispatch(login())
                }
            }
        }

        checkUserAuth().catch((error) => console.log(error))
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
                path="*"
                element={<NotFoundPage />}
            />
        </Routes>
    )
}

export default AppRoutes
