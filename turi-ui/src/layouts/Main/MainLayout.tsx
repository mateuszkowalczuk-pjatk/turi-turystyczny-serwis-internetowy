import { Outlet } from 'react-router-dom'
import { useAuthenticated } from '../../store/slices/auth.ts'
import { usePremium } from '../../store/slices/premium.ts'
import PremiumHeader from '../../components/Shared/Header/PremiumHeader'
import UserHeader from '../../components/Shared/Header/UserHeader'
import GuestHeader from '../../components/Shared/Header/GuestHeader'
import PremiumFooter from '../../components/Shared/Footer/PremiumFooter'
import UserFooter from '../../components/Shared/Footer/UserFooter'
import GuestFooter from '../../components/Shared/Footer/GuestFooter'
import styles from '../Layout.module.css'

const MainLayout = () => {
    const isAuthenticated = useAuthenticated()
    const isPremium = usePremium()

    return (
        <div className={styles.layout}>
            {isAuthenticated ? isPremium ? <PremiumHeader /> : <UserHeader /> : <GuestHeader />}
            <Outlet />
            {isAuthenticated ? isPremium ? <PremiumFooter /> : <UserFooter /> : <GuestFooter />}
        </div>
    )
}

export default MainLayout
