import AuthHeader from '../../components/Header/AuthHeader'
import NotFound from '../../components/NotFound/NotFound.tsx'
import ProfileFooter from '../../components/Footer/ProfileFooter'
import styles from '../Page.module.css'

const NotFoundPage = () => {
    return (
        <div className={styles.page}>
            <AuthHeader />
            <NotFound />
            <ProfileFooter />
        </div>
    )
}

export default NotFoundPage