import { Route, Routes } from 'react-router-dom'
import { useAuth } from '../hooks/useAuth.ts'
import MainLayout from '../layouts/Main'
import MainPage from '../pages/Main'
import OfferRoutes from './OfferRoutes.tsx'
import AuthRoutes from './AuthRoutes'
import ProfileRoutes from './ProfileRoutes'
import PremiumRoutes from './PremiumRoutes'
import TourismRoutes from './TourismRoutes'

const AppRoutes = () => {
    useAuth(null)

    return (
        <Routes>
            <Route
                path="/"
                element={<MainLayout />}
            >
                <Route
                    index
                    element={<MainPage />}
                />
            </Route>
            <Route
                path="*"
                element={<OfferRoutes />}
            />
            <Route
                path="*"
                element={<AuthRoutes />}
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
        </Routes>
    )
}

export default AppRoutes
