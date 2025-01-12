import { Route, Routes } from 'react-router-dom'
import MainLayout from '../layouts/Main'
import ReservationPage from '../pages/Reservation/ReservationPage'
import ReservationOfferPage from '../pages/Reservation/ReservationOfferPage'
import ReservationPlanPage from '../pages/Reservation/ReservationPlanPage'
import ReservationPersonalPage from '../pages/Reservation/ReservationPersonalPage'
import ReservationPaymentPage from '../pages/Reservation/ReservationPaymentPage'
import ReservationPaymentCheckPage from '../pages/Reservation/ReservationPaymentCheckPage'
import ReservationModifyPage from '../pages/Reservation/ReservationModifyPage'
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
                    element={<ReservationPage />}
                />
                <Route
                    path="offer"
                    element={<ReservationOfferPage />}
                />
                <Route
                    path="plan"
                    element={<ReservationPlanPage />}
                />
                <Route
                    path="personal"
                    element={<ReservationPersonalPage />}
                />
                <Route
                    path="payment"
                    element={<ReservationPaymentPage />}
                />
                <Route
                    path="payment-check"
                    element={<ReservationPaymentCheckPage />}
                />
                <Route
                    path="modify"
                    element={<ReservationModifyPage />}
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
