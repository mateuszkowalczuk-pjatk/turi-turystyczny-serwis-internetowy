import { Route, Routes } from 'react-router-dom';
import HomePage from '../pages/Home';
import LoginRoutes from './LoginRoutes';
import SignUpRoutes from './SignUpRoutes';
import ProfileRoutes from './ProfileRoutes';
import NotFoundPage from '../pages/NotFound';
// import UserHomePage from "../pages/UserHome";

const AppRoutes = () => {
    // const { isAuthenticated } = useContext(AuthContext);

    return (
        <Routes>
            {/*<Route path="/" element={ isAuthenticated ? <UserHomePage /> : <HomePage />}>*/}
            <Route path="/" element={<HomePage />} />
            <Route path="login/*" element={<LoginRoutes />} />
            <Route path="signup/*" element={<SignUpRoutes />} />
            <Route path="profile/*" element={<ProfileRoutes />} />
            <Route path="*" element={<NotFoundPage />} />
        </Routes>
    )
}

export default AppRoutes;