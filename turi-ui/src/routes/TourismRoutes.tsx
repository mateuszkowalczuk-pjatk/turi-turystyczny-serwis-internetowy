import { Route, Routes } from 'react-router-dom'
import TourismPage from '../pages/Tourism/TourismPage'
import NotFoundPage from '../pages/NotFound'

const TourismRoutes = () => {
    return (
        <Routes>
            <Route
                path="/"
                element={<TourismPage />}
            ></Route>
            <Route
                path="*"
                element={<NotFoundPage />}
            />
        </Routes>
    )
}

export default TourismRoutes
