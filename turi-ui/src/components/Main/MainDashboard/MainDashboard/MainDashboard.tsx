import MainDashboardBanner from '../MainDashboardBanner'
import Browser from '../../../Shared/Browser/Browser'
import styles from './MainDashboard.module.css'

const MainDashboard = () => {
    return (
        <div className={styles.dashboard}>
            <div className={styles.content}>
                <MainDashboardBanner />
                <Browser />
            </div>
        </div>
    )
}

export default MainDashboard
