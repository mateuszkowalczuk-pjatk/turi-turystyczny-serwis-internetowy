import { useSelector } from 'react-redux'
import { RootState } from '../../store/store.ts'
import MainDashboard from '../../components/Main/MainDashboard/MainDashboard'
import PremiumMainInformation from '../../components/Main/MainInformation/PremiumMainInformation'
import UserMainInformation from '../../components/Main/MainInformation/UserMainInformation'
import GuestMainInformation from '../../components/Main/MainInformation/GuestMainInformation'
import MainProposition from '../../components/Main/MainProposition/MainProposition'
import styles from '../Page.module.css'

const MainPage = () => {
    const isAuthenticated = useSelector((state: RootState) => state.auth.isAuthenticated)
    const isPremium = useSelector((state: RootState) => state.premium.isPremiumAccount)

    return (
        <div className={styles.page}>
            <MainDashboard />
            {isAuthenticated ? (
                isPremium ? (
                    <PremiumMainInformation />
                ) : (
                    <UserMainInformation />
                )
            ) : (
                <GuestMainInformation />
            )}
            <MainProposition />
        </div>
    )
}

export default MainPage
