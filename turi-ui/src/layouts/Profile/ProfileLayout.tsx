import { Outlet } from 'react-router-dom'
import styles from '../Layout.module.css'

const ProfileLayout = () => {
    // const { isAuthenticated } = useContext(AuthContext);

    return (
        <div className={styles.layout}>
            {/*Header*/}
            <Outlet />
            {/*Footer*/}
        </div>
    )
}

export default ProfileLayout;