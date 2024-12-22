import AuthHeader from '../../components/Shared/Header/AuthHeader'
import NotFound from '../../components/NotFound/NotFound'
import DefaultFooter from '../../components/Shared/Footer/DefaultFooter'
import styles from '../Page.module.css'

const NotFoundPage = () => {
    return (
        <div className={styles.page}>
            <AuthHeader />
            <NotFound />
            <DefaultFooter />
        </div>
    )
}

export default NotFoundPage
