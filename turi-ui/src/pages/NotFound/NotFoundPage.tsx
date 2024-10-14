import AuthHeader from '../../components/Header/AuthHeader'
import NotFound from '../../components/NotFound/NotFound.tsx'
import ProfileFooter from '../../components/Footer/ProfileFooter'
import styles from './NotFoundPage.module.css'

const NotFoundPage = () => {
    return (
        <div className={styles.notfound}>
            <AuthHeader />
            <NotFound />
            <ProfileFooter />
        </div>
    )
}

export default NotFoundPage;