import AuthHeader from '../../components/Header/AuthHeader'
import NotFoundContent from '../../components/NotFound/NotFoundContent'
import ProfileFooter from '../../components/Footer/ProfileFooter'
import styles from '../Page.module.css'

const NotFoundPage = () => {
    return (
        <div className={styles.page}>
            <AuthHeader />
            <NotFoundContent />
            <ProfileFooter />
        </div>
    )
}

export default NotFoundPage