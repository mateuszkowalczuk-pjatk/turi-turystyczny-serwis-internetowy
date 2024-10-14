import { Outlet } from 'react-router-dom'
import AuthHeader from '../../components/AuthHeader'
import AuthContent from '../../components/AuthContent'
import AuthFooter from '../../components/AuthFooter'
import styles from '../Layout.module.css'

const AuthLayout = () => {
    return (
        <div className={styles.layout}>
            <AuthHeader />
            <AuthContent
                content={ <Outlet /> }
            />
            <AuthFooter  />
        </div>
    )
}

export default AuthLayout;