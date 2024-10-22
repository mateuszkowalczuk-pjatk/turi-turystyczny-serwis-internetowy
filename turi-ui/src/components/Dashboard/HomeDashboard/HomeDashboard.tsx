import DashboardBanner from '../DashboardBanner'
import BrowserContent from '../../Browser/BrowserContent'
import styles from './HomeDashboard.module.css'

const HomeDashboard = () => {
    return (
        <div className={styles.dashboard}>
            <DashboardBanner />
            <BrowserContent />
        </div>
    )
}

export default HomeDashboard