import { Route, Routes } from 'react-router-dom'
import MainLayout from '../layouts/Main'
import SearchPage from '../pages/Offer/SearchPage'
import OfferPage from '../pages/Offer/OfferPage'
import ReservationsPage from '../pages/Reservation/ReservationsPage'
import ReservationRealizedPage from '../pages/Reservation/ReservationsRealizedPage'
import FavouritePage from '../pages/Offer/FavouritePage'
import NotFoundPage from '../pages/NotFound'

const OfferRoutes = () => {
    return (
        <Routes>
            <Route
                path="/"
                element={<MainLayout />}
            >
                <Route
                    path="search"
                    element={<SearchPage />}
                />
                <Route
                    path="offer"
                    element={<OfferPage />}
                />
                <Route
                    path="reservations"
                    element={<ReservationsPage />}
                />
                <Route
                    path="realized"
                    element={<ReservationRealizedPage />}
                />
                <Route
                    path="favourites"
                    element={<FavouritePage />}
                />
            </Route>
            <Route
                path="*"
                element={<NotFoundPage />}
            />
        </Routes>
    )
}

export default OfferRoutes
