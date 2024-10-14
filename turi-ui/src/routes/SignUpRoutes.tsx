import { Route, Routes } from "react-router-dom";
import AuthLayout from '../layouts/Auth'
import SignUpPage from "../pages/SignUp/SignUpPage";
import SignUpVerificationPage from '../pages/SignUp/SignUpVerificationPage'
import SignUpPersonalizationPage from '../pages/SignUp/SignUpPersonalizationPage'
import NotFoundPage from '../pages/NotFound'

const SignUpRoutes = () => {
    return (
        <Routes>
            <Route path="/" element={<AuthLayout />}>
                <Route index element={<SignUpPage />} />
                <Route path="/verification" element={<SignUpVerificationPage />} />
                <Route path="/personalization" element={<SignUpPersonalizationPage />} />
            </Route>
            <Route path="*" element={<NotFoundPage />} />
        </Routes>
    )
}

export default SignUpRoutes