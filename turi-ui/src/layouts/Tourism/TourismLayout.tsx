import { Outlet } from 'react-router-dom'
import PremiumHeader from '../../components/Shared/Header/PremiumHeader'
import PremiumFooter from '../../components/Shared/Footer/PremiumFooter'
import styles from '../Layout.module.css'

const TourismLayout = () => {
    return (
        <div className={styles.layout}>
            <PremiumHeader />
            <Outlet />
            <PremiumFooter />
        </div>
    )
}

export default TourismLayout
