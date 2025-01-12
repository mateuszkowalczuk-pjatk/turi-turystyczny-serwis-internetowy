import { Outlet } from 'react-router-dom'
import Layout from '../../components/Shared/Layout'
import AuthHeader from '../../components/Shared/Header/AuthHeader'
import AuthContent from '../../components/Shared/Contents/AuthContent'
import AuthFooter from '../../components/Shared/Footer/AuthFooter'

const AuthLayout = () => {
    return (
        <Layout
            header={<AuthHeader />}
            content={<AuthContent content={<Outlet />} />}
            footer={<AuthFooter />}
        />
    )
}

export default AuthLayout
