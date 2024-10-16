import { Route, Routes } from 'react-router-dom'
import ProfileLayout from '../layouts/Profile'
import ProfilePage from '../pages/Profile/ProfilePage'
import ProfilePersonalPage from '../pages/Profile/ProfilePersonalPage'
import ProfilePreferencePage from '../pages/Profile/ProfilePreferencePage'
import NotFoundPage from '../pages/NotFound'

const ProfileRoutes = () => {
    return (
        <Routes>
            <Route path="/" element={<ProfileLayout />}>
                <Route index element={<ProfilePage />} />
                <Route path="/personal" element={<ProfilePersonalPage />} />
                <Route path="/preference" element={<ProfilePreferencePage />} />
            </Route>
            <Route path="*" element={<NotFoundPage />} />
        </Routes>
    )
}

export default ProfileRoutes;