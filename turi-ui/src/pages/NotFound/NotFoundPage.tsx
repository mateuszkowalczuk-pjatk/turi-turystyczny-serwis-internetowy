import AuthHeader from '../../components/Header/AuthHeader'
import NotFoundContent from '../../components/NotFound/NotFoundContent'
import DefaultFooter from '../../components/Footer/DefaultFooter'
import styles from '../Page.module.css'

const NotFoundPage = () => {
    return (
        <div className={styles.page}>
            <AuthHeader />
            <NotFoundContent />
            <DefaultFooter />
        </div>
    )
}

export default NotFoundPage
