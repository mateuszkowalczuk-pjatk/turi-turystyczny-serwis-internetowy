import { Outlet } from 'react-router-dom'
import Header from '../../components/Header'
import Footer from '../../components/Footer'
import styles from '../Layout.module.css'

const MainLayout = () => {
    // const { isAuthenticated } = useContext(AuthContext);

    return (
        <div className={styles.layout}>
            <Header />
            <Outlet />
            <Footer />
        </div>
    )
}

export default MainLayout;