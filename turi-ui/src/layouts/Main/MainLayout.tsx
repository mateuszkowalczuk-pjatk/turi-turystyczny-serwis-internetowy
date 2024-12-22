import { RootState } from '../../store/store'
import { Outlet } from 'react-router-dom'
import { useSelector } from 'react-redux'
import PremiumHeader from '../../components/Shared/Header/PremiumHeader'
import UserHeader from '../../components/Shared/Header/UserHeader'
import GuestHeader from '../../components/Shared/Header/GuestHeader'
import PremiumFooter from '../../components/Shared/Footer/PremiumFooter'
import UserFooter from '../../components/Shared/Footer/UserFooter'
import GuestFooter from '../../components/Shared/Footer/GuestFooter'
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
