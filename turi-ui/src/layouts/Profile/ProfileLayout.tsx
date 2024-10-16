import { Outlet } from 'react-router-dom'
import UserHeader from '../../components/Header/UserHeader'
import ProfileTitle from '../../components/Profile/ProfileTitle'
import ProfileContent from '../../components/Profile/ProfileContent'
import ProfileFooter from '../../components/Footer/ProfileFooter'
import styles from '../Layout.module.css'

const ProfileLayout = () => {
    return (
        <div className={styles.layout}>
            <UserHeader />
            <ProfileTitle />
            <ProfileContent
                content={ <Outlet /> }
            />
            <ProfileFooter />
        </div>
    )
}

export default ProfileLayout;