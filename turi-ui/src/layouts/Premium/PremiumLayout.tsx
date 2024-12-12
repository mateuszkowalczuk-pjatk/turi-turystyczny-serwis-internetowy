import { RootState } from '../../store/store.ts'
import { Outlet } from 'react-router-dom'
import PremiumHeader from '../../components/Header/PremiumHeader'
import UserHeader from '../../components/Header/UserHeader'
import PremiumContent from '../../components/Premium/PremiumContent'
import ProfileFooter from '../../components/Footer/ProfileFooter'
import { useSelector } from 'react-redux'
import styles from '../Layout.module.css'

const PremiumLayout = () => {
    const isPremium = useSelector((state: RootState) => state.premium.isPremiumAccount)

    return (
        <div className={styles.layout}>
            {isPremium ? <PremiumHeader /> : <UserHeader />}
            <PremiumContent content={<Outlet />} />
            <ProfileFooter />
        </div>
    )
}

export default PremiumLayout
