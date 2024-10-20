import { Outlet } from 'react-router-dom'
import AuthHeader from '../../components/Header/AuthHeader'
import AuthContent from '../../components/Auth/AuthContent'
import AuthFooter from '../../components/Footer/AuthFooter'
import styles from '../Layout.module.css'

const AuthLayout = () => {
    return (
        <div className={styles.layout}>
            <AuthHeader />
            <AuthContent
                content={ <Outlet /> }
            />
            <AuthFooter />
        </div>
    )
}

export default AuthLayout