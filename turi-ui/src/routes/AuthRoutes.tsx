import { Route, Routes } from 'react-router-dom'
import AuthLayout from '../layouts/Auth'
import RegisterPage from '../pages/Auth/RegisterPage'
import ActivationPage from '../pages/Auth/ActivationPage'
import LoginPage from '../pages/Auth/LoginPage'
import PersonalPage from '../pages/Auth/PersonalPage'
import LoginCodePage from '../pages/Auth/LoginCodePage'
import ResetPasswordEmailCheckPage from '../pages/Auth/ResetPasswordEmailCheckPage'
import ResetPasswordCodePage from '../pages/Auth/ResetPasswordCodePage'
import ResetPasswordPage from '../pages/Auth/ResetPasswordPage'
import OfferRoutes from './OfferRoutes.tsx'

const AuthRoutes = () => {
    return (
        <Routes>
            <Route
                path="/"
                element={<AuthLayout />}
            >
                <Route
                    path="register"
                    element={<RegisterPage />}
                />
                <Route
                    path="activate"
                    element={<ActivationPage />}
                />
                <Route
                    path="login"
                    element={<LoginPage />}
                />
                <Route
                    path="personal"
                    element={<PersonalPage />}
                />
                <Route
                    path="login/code"
                    element={<LoginCodePage />}
                />
                <Route
                    path="reset-password/email-check"
                    element={<ResetPasswordEmailCheckPage />}
                />
                <Route
                    path="reset-password/code"
                    element={<ResetPasswordCodePage />}
                />
                <Route
                    path="reset-password"
                    element={<ResetPasswordPage />}
                />
            </Route>
            <Route
                path="*"
                element={<OfferRoutes />}
            />
        </Routes>
    )
}

export default AuthRoutes
