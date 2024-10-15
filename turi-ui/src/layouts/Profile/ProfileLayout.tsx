import { Outlet } from 'react-router-dom'
import UserHeader from '../../components/Header/UserHeader'
import ProfileFooter from '../../components/Footer/ProfileFooter'
import styles from '../Layout.module.css'

const ProfileLayout = () => {
    return (
        <div className={styles.layout}>
            <UserHeader />
            {/*dodanie zagnieżdzenia z ProfileContent*/}
            <Outlet />
            <ProfileFooter />
        </div>
    )
}

export default ProfileLayout;