import { Route, Routes } from 'react-router-dom'
import CustomLayout from '../layouts/Custom'
import ProfilePage from '../pages/Profile/ProfilePage'
import ProfilePersonalPage from '../pages/Profile/ProfilePersonalPage'
import ProfilePreferencePage from '../pages/Profile/ProfilePreferencePage'
import ProfilePremiumPage from '../pages/Profile/ProfilePremiumPage'
import NotFoundPage from '../pages/NotFound'

const ProfileRoutes = () => {
    return (
        <Routes>
            <Route
                path="/"
                element={<CustomLayout profile />}
            >
                <Route
                    index
                    element={<ProfilePage />}
                />
                <Route
                    path="personal"
                    element={<ProfilePersonalPage />}
                />
                <Route
                    path="preference"
                    element={<ProfilePreferencePage />}
                />
                <Route
                    path="premium"
                    element={<ProfilePremiumPage />}
                />
            </Route>
            <Route
                path="*"
                element={<NotFoundPage />}
            />
        </Routes>
    )
}

export default ProfileRoutes
