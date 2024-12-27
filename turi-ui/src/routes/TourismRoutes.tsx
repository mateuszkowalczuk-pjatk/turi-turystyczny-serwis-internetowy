import { Route, Routes } from 'react-router-dom'
import TourismLayout from '../layouts/Tourism'
import TourismPage from '../pages/Tourism/TourismPage'
import TourismStaysPlanPage from '../pages/Tourism/TourismStaysPlanPage'
import TourismReservationsPlanPage from '../pages/Tourism/TourismReservationsPlanPage'
import TourismStayOfferPage from '../pages/Tourism/TourismStayOfferPage'
import TourismAttractionOfferPage from '../pages/Tourism/TourismAttractionOfferPage'
import TourismStatisticsPage from '../pages/Tourism/TourismStatisticsPage'
import NotFoundPage from '../pages/NotFound'

const TourismRoutes = () => {
    return (
        <Routes>
            <Route
                path="/"
                element={<TourismLayout />}
            >
                <Route
                    index
                    element={<TourismPage />}
                />
                <Route
                    path="stays-plan"
                    element={<TourismStaysPlanPage />}
                />
                <Route
                    path="reservations-plan"
                    element={<TourismReservationsPlanPage />}
                />
                <Route
                    path="create-stay-offer"
                    element={<TourismStayOfferPage />}
                />
                <Route
                    path="create-attraction-offer"
                    element={<TourismAttractionOfferPage />}
                />
                <Route
                    path="modify-stay-offer"
                    element={<TourismStayOfferPage modify />}
                />
                <Route
                    path="modify-attraction-offer"
                    element={<TourismAttractionOfferPage modify />}
                />
                <Route
                    path="statistics"
                    element={<TourismStatisticsPage />}
                />
            </Route>
            <Route
                path="*"
                element={<NotFoundPage />}
            />
        </Routes>
    )
}

export default TourismRoutes
