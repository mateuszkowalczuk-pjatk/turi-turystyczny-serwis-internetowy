import MainDashboardBanner from '../MainDashboardBanner'
import Browser from '../../../Shared/Browser/Browser'
import { SearchMode } from '../../../../types/search.ts'
import styles from './MainDashboard.module.css'

const MainDashboard = () => {
    return (
        <div className={styles.dashboard}>
            <div className={styles.content}>
                <MainDashboardBanner />
                <Browser
                    defaultMode={SearchMode.ALL}
                    defaultQuery={''}
                    defaultDateFrom={null}
                    defaultDateTo={null}
                />
            </div>
        </div>
    )
}

export default MainDashboard
