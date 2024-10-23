import { Route, Routes } from 'react-router-dom'
import MainLayout from '../layouts/Main'
import HomePage from '../pages/Home'
import LoginRoutes from './LoginRoutes'
import SignUpRoutes from './SignUpRoutes'
import ProfileRoutes from './ProfileRoutes'
import NotFoundPage from '../pages/NotFound'

const AppRoutes = () => {
    return (
        <Routes>
            <Route
                path="/"
                element={<MainLayout />}
            >
                <Route
                    index
                    element={<HomePage />}
                />
            </Route>
            <Route
                path="login/*"
                element={<LoginRoutes />}
            />
            <Route
                path="signup/*"
                element={<SignUpRoutes />}
            />
            <Route
                path="profile/*"
                element={<ProfileRoutes />}
            />
            <Route
                path="*"
                element={<NotFoundPage />}
            />
        </Routes>
    )
}

export default AppRoutes
