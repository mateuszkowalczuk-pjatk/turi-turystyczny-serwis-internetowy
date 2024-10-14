import { useSelector } from 'react-redux'
import { RootState } from '../../store/store.ts'
import Dashboard from '../../components/Dashboard'
import UserInformation from '../../components/Information/UserInformation'
import Information from '../../components/Information/Information'
import Proposition from '../../components/Proposition'
import styles from './HomePage.module.css'

const HomePage = () => {
    const isAuthenticated = useSelector((state: RootState) => state.auth.isAuthenticated);

    return (
        <div className={styles.home}>
            <Dashboard />
            {isAuthenticated ? ( <UserInformation /> ) : ( <Information /> )}
            <Proposition />
        </div>
    )
}

export default HomePage;