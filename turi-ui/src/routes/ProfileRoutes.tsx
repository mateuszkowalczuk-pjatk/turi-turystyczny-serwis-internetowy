import { Route, Routes } from 'react-router-dom'
import ProfileLayout from '../layouts/Profile'
import ProfilePage from '../pages/Profile/ProfilePage'
import ProfileAccountPage from '../pages/Profile/ProfileAccountPage'
import ProfilePersonalPage from '../pages/Profile/ProfilePersonalPage'
import ProfilePreferencesPage from '../pages/Profile/ProfilePreferencesPage'
import NotFoundPage from '../pages/NotFound'

const ProfileRoutes = () => {
    return (
        <Routes>
            <Route path="/" element={<ProfileLayout />}>
                <Route index element={<ProfilePage />} />
                <Route path="/account" element={<ProfileAccountPage />} />
                <Route path="/personal" element={<ProfilePersonalPage />} />
                <Route path="/preferences" element={<ProfilePreferencesPage />} />
            </Route>
            <Route path="*" element={<NotFoundPage />} />
        </Routes>
    )
}

export default ProfileRoutes;