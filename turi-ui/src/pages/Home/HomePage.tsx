import { useSelector } from 'react-redux'
import { RootState } from '../../store/store.ts'
import HomeDashboard from '../../components/Dashboard/HomeDashboard'
import UserInformation from '../../components/Information/UserInformation'
import PremiumInformation from '../../components/Information/PremiumInformation/PremiumInformation.tsx'
import GuestInformation from '../../components/Information/GuestInformation'
import HomeProposition from '../../components/Proposition/HomeProposition'
import styles from '../Page.module.css'

const HomePage = () => {
    const isAuthenticated = useSelector((state: RootState) => state.auth.isAuthenticated)
    const isPremiumAccount = useSelector((state: RootState) => state.premium.isPremiumAccount)

    return (
        <div className={styles.page}>
            <HomeDashboard />
            {isAuthenticated ? isPremiumAccount ? <PremiumInformation /> : <UserInformation /> : <GuestInformation />}
            <HomeProposition />
        </div>
    )
}

export default HomePage
