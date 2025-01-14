import { Route, Routes } from 'react-router-dom'
import MainLayout from '../layouts/Main'
import SearchPage from '../pages/Offer/SearchPage'
import OfferPage from '../pages/Offer/OfferPage'
import FavouritePage from '../pages/Offer/FavouritePage'

const AuthRoutes = () => {
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
                    path="favourite"
                    element={<FavouritePage />}
                />
            </Route>
        </Routes>
    )
}

export default AuthRoutes
