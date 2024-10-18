import { useSelector } from 'react-redux'
import { RootState } from '../../store/store.ts'
import Dashboard from '../../components/Dashboard'
import UserInformation from '../../components/Information/UserInformation'
import GuestInformation from '../../components/Information/GuestInformation'
import Proposition from '../../components/Proposition'
import styles from '../Page.module.css'

const HomePage = () => {
    const isAuthenticated = useSelector((state: RootState) => state.auth.isAuthenticated);

    return (
        <div className={styles.page}>
            <Dashboard />
            { isAuthenticated ? ( <UserInformation /> ) : ( <GuestInformation /> ) }
            <Proposition />
        </div>
    )
}

export default HomePage;