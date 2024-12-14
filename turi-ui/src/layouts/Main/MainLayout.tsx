import { useSelector } from 'react-redux'
import { RootState } from '../../store/store'
import { Outlet } from 'react-router-dom'
import PremiumHeader from '../../components/Header/PremiumHeader'
import UserHeader from '../../components/Header/UserHeader'
import GuestHeader from '../../components/Header/GuestHeader'
import PremiumFooter from '../../components/Footer/PremiumFooter'
import UserFooter from '../../components/Footer/UserFooter'
import GuestFooter from '../../components/Footer/GuestFooter'
import styles from '../Layout.module.css'

const MainLayout = () => {
    const isAuthenticated = useSelector((state: RootState) => state.auth.isAuthenticated)
    const isPremiumAccount = useSelector((state: RootState) => state.premium.isPremiumAccount)

    return (
        <div className={styles.layout}>
            {isAuthenticated ? isPremiumAccount ? <PremiumHeader /> : <UserHeader /> : <GuestHeader />}
            <Outlet />
            {isAuthenticated ? isPremiumAccount ? <PremiumFooter /> : <UserFooter /> : <GuestFooter />}
        </div>
    )
}

export default MainLayout
