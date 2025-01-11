import { Route, Routes } from 'react-router-dom'
import MainLayout from '../layouts/Main'
import ReservationPage from '../pages/Reservation/ReservationPage'
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
                {/*<Route*/}
                {/*    path="personal"*/}
                {/*    element={<ReservationPersonalPage />}*/}
                {/*/>*/}
                {/*<Route*/}
                {/*    path="payment"*/}
                {/*    element={<ReservationPaymentPage />}*/}
                {/*/>*/}
                {/*<Route*/}
                {/*    path="payment-check"*/}
                {/*    element={<ReservationPaymentCheckPage />}*/}
                {/*/>*/}
                {/*<Route*/}
                {/*    path="summary"*/}
                {/*    element={<ReservationSummaryPage />}*/}
                {/*/>*/}
            </Route>
            <Route
                path="*"
                element={<NotFoundPage />}
            />
        </Routes>
    )
}

export default ReservationRoutes
