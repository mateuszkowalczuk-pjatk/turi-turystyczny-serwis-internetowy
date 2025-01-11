import { Route, Routes } from 'react-router-dom'
import MainLayout from '../layouts/Main'
import InfoPage from '../pages/Info/InfoPage'
import InfoTermsOfServicePage from '../pages/Info/InfoTermsOfServicePage'
import InfoPrivacyPolicyPage from '../pages/Info/InfoPrivacyPolicyPage'
import NotFoundPage from '../pages/NotFound'

const ReservationRoutes = () => {
    return (
        <Routes>
            <Route
                path="/"
                element={<MainLayout />}
            >
                <Route
                    index
                    element={<InfoPage />}
                />
                <Route
                    path="terms-of-service"
                    element={<InfoTermsOfServicePage />}
                />
                <Route
                    path="privacy-policy"
                    element={<InfoPrivacyPolicyPage />}
                />
            </Route>
            <Route
                path="*"
                element={<NotFoundPage />}
            />
        </Routes>
    )
}

export default ReservationRoutes
