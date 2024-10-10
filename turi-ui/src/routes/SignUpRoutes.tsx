import { Route, Routes } from "react-router-dom";
import SignUpPage from "../pages/SignUpPage";

const SignUpRoutes = () => {
    return (
        <Routes>
            <Route path="/" element={<SignUpPage />} />
        </Routes>
    )
}

export default SignUpRoutes