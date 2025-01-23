import { Route, Routes } from 'react-router-dom'
import MainLayout from '../layouts/Main'
import TourismPage from '../pages/Tourism/TourismPage'
import TourismStayOfferPage from '../pages/Tourism/TourismStayOfferPage'
import TourismAttractionOfferPage from '../pages/Tourism/TourismAttractionOfferPage'
import NotFoundPage from '../pages/NotFound'

const TourismRoutes = () => {
    return (
        <Routes>
            <Route
                path="/"
                element={<MainLayout />}
            >
                <Route
                    index
                    element={<TourismPage />}
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
            </Route>
            <Route
                path="*"
                element={<NotFoundPage />}
            />
        </Routes>
    )
}

export default TourismRoutes
