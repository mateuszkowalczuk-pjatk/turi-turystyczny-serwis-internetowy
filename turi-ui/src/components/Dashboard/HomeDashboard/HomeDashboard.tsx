import DashboardLayout from '../DashboardLayout'
import DashboardBanner from '../DashboardBanner'
import BrowserContent from '../../Browser/BrowserContent'
import styles from './HomeDashboard.module.css'

const HomeDashboard = () => {
    return (
        <DashboardLayout
            content={
                <div className={styles.content}>
                    <DashboardBanner />
                    <BrowserContent />
                </div>
            }
        />
    )
}

export default HomeDashboard