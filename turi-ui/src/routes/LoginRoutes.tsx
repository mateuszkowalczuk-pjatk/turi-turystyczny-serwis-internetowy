import { Route, Routes } from 'react-router-dom'
import AuthLayout from '../layouts/Auth'
import LoginPage from '../pages/Login/LoginPage'
import LoginPasswordCheckPage from '../pages/Login/LoginPasswordCheckPage'
import LoginPasswordCodePage from '../pages/Login/LoginPasswordCodePage'
import LoginPasswordResetPage from '../pages/Login/LoginPasswordResetPage'
import NotFoundPage from '../pages/NotFound'

const LoginRoutes = () => {
    return (
        <Routes>
            <Route
                path="/"
                element={<AuthLayout />}
            >
                <Route
                    index
                    element={<LoginPage />}
                />
                <Route
                    path="/check"
                    element={<LoginPasswordCheckPage />}
                />
                <Route
                    path="/code"
                    element={<LoginPasswordCodePage />}
                />
                <Route
                    path="/reset"
                    element={<LoginPasswordResetPage />}
                />
            </Route>
            <Route
                path="*"
                element={<NotFoundPage />}
            />
        </Routes>
    )
}

export default LoginRoutes
