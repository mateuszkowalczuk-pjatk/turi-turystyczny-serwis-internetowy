import { Route, Routes } from 'react-router-dom'
import AuthLayout from '../layouts/Auth'
import SignUpPage from '../pages/SignUp/SignUpPage'
import SignUpVerifyPage from '../pages/SignUp/SignUpVerifyPage'
import SignUpPersonalPage from '../pages/SignUp/SignUpPersonalPage'
import NotFoundPage from '../pages/NotFound'

const SignUpRoutes = () => {
    return (
        <Routes>
            <Route
                path="/"
                element={<AuthLayout />}
            >
                <Route
                    index
                    element={<SignUpPage />}
                />
                <Route
                    path="/verify"
                    element={<SignUpVerifyPage />}
                />
                <Route
                    path="/personal"
                    element={<SignUpPersonalPage />}
                />
            </Route>
            <Route
                path="*"
                element={<NotFoundPage />}
            />
        </Routes>
    )
}

export default SignUpRoutes
