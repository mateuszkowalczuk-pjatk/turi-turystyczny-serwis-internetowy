import { Route, Routes } from 'react-router-dom'
import PremiumLayout from '../layouts/Premium'
import PremiumPage from '../pages/Premium/PremiumPage'
import PremiumVerifyPage from '../pages/Premium/PremiumVerifyPage'
import PremiumPaymentPage from '../pages/Premium/PremiumPaymentPage'
import PremiumPaymentCheckPage from '../pages/Premium/PremiumPaymentCheckPage'
import PremiumSummaryPage from '../pages/Premium/PremiumSummaryPage'
import NotFoundPage from '../pages/NotFound'

const PremiumRoutes = () => {
    return (
        <Routes>
            <Route
                path="/"
                element={<PremiumLayout />}
            >
                <Route
                    index
                    element={<PremiumPage />}
                />
                <Route
                    path="verify"
                    element={<PremiumVerifyPage />}
                />
                <Route
                    path="payment"
                    element={<PremiumPaymentPage />}
                />
                <Route
                    path="payment-check"
                    element={<PremiumPaymentCheckPage />}
                />
                <Route
                    path="summary"
                    element={<PremiumSummaryPage />}
                />
            </Route>
            <Route
                path="*"
                element={<NotFoundPage />}
            />
        </Routes>
    )
}

export default PremiumRoutes
