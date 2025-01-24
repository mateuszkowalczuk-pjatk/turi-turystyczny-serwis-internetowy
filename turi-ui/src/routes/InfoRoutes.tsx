import { Route, Routes } from 'react-router-dom'
import DefaultLayout from '../layouts/Default'
import InfoPage from '../pages/Info/InfoPage'
import InfoTermsOfUsePage from '../pages/Info/InfoTermsOfUsePage'
import InfoPrivacyPolicyPage from '../pages/Info/InfoPrivacyPolicyPage'
import NotFoundPage from '../pages/NotFound'

const InfoRoutes = () => {
    return (
        <Routes>
            <Route
                path="/"
                element={<DefaultLayout />}
            >
                <Route
                    index
                    element={<InfoPage />}
                />
                <Route
                    path="terms-of-use"
                    element={<InfoTermsOfUsePage />}
                />
                <Route
                    path="privacy-policy"
                    element={<InfoPrivacyPolicyPage />}
                />
            </Route>
            <Route
                path="*"
                element={<NotFoundPage />}
            />
        </Routes>
    )
}

export default InfoRoutes
