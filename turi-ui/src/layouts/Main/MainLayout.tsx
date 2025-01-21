import { useStates } from '../../hooks/useStates.ts'
import { Outlet } from 'react-router-dom'
import Loader from '../../components/Shared/Loading/Loader'
import Layout from '../../components/Shared/Layout'
import PremiumHeader from '../../components/Shared/Header/PremiumHeader'
import UserHeader from '../../components/Shared/Header/UserHeader'
import GuestHeader from '../../components/Shared/Header/GuestHeader'
import MainContent from '../../components/Shared/Contents/MainContent/MainContent.tsx'
import PremiumFooter from '../../components/Shared/Footer/PremiumFooter'
import UserFooter from '../../components/Shared/Footer/UserFooter'
import GuestFooter from '../../components/Shared/Footer/GuestFooter'

const MainLayout = () => {
    const { isAuthenticated, isPremium } = useStates()

    return (
        <Loader>
            <Layout
                header={isAuthenticated ? isPremium ? <PremiumHeader /> : <UserHeader /> : <GuestHeader />}
                content={<MainContent content={<Outlet />} />}
                footer={isAuthenticated ? isPremium ? <PremiumFooter /> : <UserFooter /> : <GuestFooter />}
            />
        </Loader>
    )
}

export default MainLayout
