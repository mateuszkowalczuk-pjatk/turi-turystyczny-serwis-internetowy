import { useSelector } from 'react-redux'
import { Outlet } from 'react-router-dom'
import UserHeader from '../../components/Header/UserHeader'
import PremiumHeader from '../../components/Header/PremiumHeader'
import ProfileContent from '../../components/Profile/ProfileContent'
import DefaultFooter from '../../components/Footer/DefaultFooter'
import { RootState } from '../../store/store.ts'
import styles from '../Layout.module.css'

const ProfileLayout = () => {
    const isPremiumAccount = useSelector((state: RootState) => state.premium.isPremiumAccount)

    return (
        <div className={styles.layout}>
            {isPremiumAccount ? <PremiumHeader /> : <UserHeader />}
            <ProfileContent content={<Outlet />} />
            <DefaultFooter />
        </div>
    )
}

export default ProfileLayout
