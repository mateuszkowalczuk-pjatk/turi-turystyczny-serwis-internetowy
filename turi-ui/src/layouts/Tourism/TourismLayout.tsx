import styles from '../Layout.module.css'
import PremiumHeader from '../../components/Header/PremiumHeader'
import PremiumFooter from '../../components/Footer/PremiumFooter'

const TourismLayout = () => {
    return (
        <div className={styles.layout}>
            <PremiumHeader />

            <PremiumFooter />
        </div>
    )
}

export default TourismLayout
