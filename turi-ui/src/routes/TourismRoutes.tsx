import { Route, Routes } from 'react-router-dom'
import TourismLayout from '../layouts/Tourism'
import TourismPage from '../pages/Tourism/TourismPage'
import TourismStaysPlanPage from '../pages/Tourism/TourismStaysPlanPage'
import TourismReservationsPlanPage from '../pages/Tourism/TourismReservationsPlanPage'
import TourismCreateStayOfferPage from '../pages/Tourism/TourismCreateStayOfferPage'
import TourismCreateAttractionOfferPage from '../pages/Tourism/TourismCreateAttractionOfferPage'
import TourismModifyStayOfferPage from '../pages/Tourism/TourismModifyStayOfferPage/TourismModifyStayOfferPage'
import TourismModifyAttractionOfferPage from '../pages/Tourism/TourismModifyAttractionOfferPage'
import TourismStayOfferPage from '../pages/Tourism/TourismStayOfferPage'
import TourismAttractionOfferPage from '../pages/Tourism/TourismAttractionOfferPage'
import TourismReservationOfferPage from '../pages/Tourism/TourismReservationOfferPage'
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
                    element={<TourismCreateStayOfferPage />}
                />
                <Route
                    path="create-attraction-offer"
                    element={<TourismCreateAttractionOfferPage />}
                />
                <Route
                    path="modify-stay-offer"
                    element={<TourismModifyStayOfferPage />}
                />
                <Route
                    path="modify-attraction-offer"
                    element={<TourismModifyAttractionOfferPage />}
                />
                <Route
                    path="stay-offer"
                    element={<TourismStayOfferPage />}
                />
                <Route
                    path="attraction-offer"
                    element={<TourismAttractionOfferPage />}
                />
                <Route
                    path="reservation-offer"
                    element={<TourismReservationOfferPage />}
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
