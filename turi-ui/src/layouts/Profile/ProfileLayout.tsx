import { Outlet } from 'react-router-dom'
import { usePremium } from '../../store/slices/premium.ts'
import PremiumHeader from '../../components/Shared/Header/PremiumHeader'
import UserHeader from '../../components/Shared/Header/UserHeader'
import ProfileContent from '../../components/Profile/ProfileContent'
import DefaultFooter from '../../components/Shared/Footer/DefaultFooter'
import styles from '../Layout.module.css'

const ProfileLayout = () => {
    const isPremium = usePremium()

    return (
        <div className={styles.layout}>
            {isPremium ? <PremiumHeader /> : <UserHeader />}
            <ProfileContent content={<Outlet />} />
            <DefaultFooter />
        </div>
    )
}

export default ProfileLayout
