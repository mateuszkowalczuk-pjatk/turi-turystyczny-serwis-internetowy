import { useSelector } from 'react-redux'
import { RootState } from '../../store/store.ts'
import HomeDashboard from '../../components/Dashboard/HomeDashboard'
import UserInformation from '../../components/Information/UserInformation'
import GuestInformation from '../../components/Information/GuestInformation'
import HomeProposition from '../../components/Proposition/HomeProposition'
import styles from '../Page.module.css'

const HomePage = () => {
    const isAuthenticated = useSelector((state: RootState) => state.auth.isAuthenticated)

    return (
        <div className={styles.page}>
            <HomeDashboard />
            {isAuthenticated ? <UserInformation /> : <GuestInformation />}
            <HomeProposition />
        </div>
    )
}

export default HomePage
