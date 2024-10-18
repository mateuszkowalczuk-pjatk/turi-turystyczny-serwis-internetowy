import { useSelector } from 'react-redux'
import { RootState } from '../../store/store'
import { Outlet } from 'react-router-dom'
import UserHeader from '../../components/Header/UserHeader'
import Header from '../../components/Header/Header'
import UserFooter from '../../components/Footer/UserFooter'
import GuestFooter from '../../components/Footer/GuestFooter'
import styles from '../Layout.module.css'


const MainLayout = () => {
    const isAuthenticated = useSelector((state: RootState) => state.auth.isAuthenticated);

    return (
        <div className={styles.layout}>
            {isAuthenticated ? ( <UserHeader /> ) : ( <Header /> )}
            <Outlet />
            {isAuthenticated ? ( <UserFooter /> ) : ( <GuestFooter /> )}
        </div>
    )
}

export default MainLayout;