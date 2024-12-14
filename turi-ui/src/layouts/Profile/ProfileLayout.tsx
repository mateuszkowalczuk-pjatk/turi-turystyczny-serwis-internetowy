import { Outlet } from 'react-router-dom'
import UserHeader from '../../components/Header/UserHeader'
import PremiumHeader from '../../components/Header/PremiumHeader'
import ProfileContent from '../../components/Profile/ProfileContent'
import ProfileFooter from '../../components/Footer/ProfileFooter'
import styles from '../Layout.module.css'
import { useSelector } from 'react-redux'
import { RootState } from '../../store/store.ts'

const ProfileLayout = () => {
    const isPremiumAccount = useSelector((state: RootState) => state.premium.isPremiumAccount)

    return (
        <div className={styles.layout}>
            {isPremiumAccount ? <PremiumHeader /> : <UserHeader />}
            <ProfileContent content={<Outlet />} />
            <ProfileFooter />
        </div>
    )
}

export default ProfileLayout
