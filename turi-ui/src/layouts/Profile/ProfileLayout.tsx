import { RootState } from '../../store/store.ts'
import { Outlet } from 'react-router-dom'
import { useSelector } from 'react-redux'
import PremiumHeader from '../../components/Shared/Header/PremiumHeader'
import UserHeader from '../../components/Shared/Header/UserHeader'
import ProfileContent from '../../components/Profile/ProfileContent'
import DefaultFooter from '../../components/Shared/Footer/DefaultFooter'
import styles from '../Layout.module.css'

const ProfileLayout = () => {
    const isPremium = useSelector((state: RootState) => state.premium.isPremiumAccount)

    return (
        <div className={styles.layout}>
            {isPremium ? <PremiumHeader /> : <UserHeader />}
            <ProfileContent content={<Outlet />} />
            <DefaultFooter />
        </div>
    )
}

export default ProfileLayout
