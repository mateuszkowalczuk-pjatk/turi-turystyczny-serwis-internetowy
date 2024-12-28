import { useAuthenticated } from '../../store/slices/auth.ts'
import { usePremium } from '../../store/slices/premium.ts'
import MainDashboard from '../../components/Main/MainDashboard/MainDashboard'
import PremiumMainInformation from '../../components/Main/MainInformation/PremiumMainInformation'
import UserMainInformation from '../../components/Main/MainInformation/UserMainInformation'
import GuestMainInformation from '../../components/Main/MainInformation/GuestMainInformation'
import MainProposition from '../../components/Main/MainProposition/MainProposition'
import styles from '../Page.module.css'

const MainPage = () => {
    const isAuthenticated = useAuthenticated()
    const isPremium = usePremium()

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
