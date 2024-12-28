import { useRedirectSome } from '../../../hooks/useRedirect.ts'
import { useAuthenticated } from '../../../store/slices/auth.ts'
import { usePersonal } from '../../../store/slices/personal.ts'
import AuthPersonalTitle from '../../../components/Auth/AuthPersonal/AuthPersonalTitle'
import AuthPersonalDescription from '../../../components/Auth/AuthPersonal/AuthPersonalDescription'
import AuthPersonalContent from '../../../components/Auth/AuthPersonal/AuthPersonalContent'
import styles from './PersonalPage.module.css'

const PersonalPage = () => {
    const isAuthenticated = useAuthenticated()
    const isPersonalization = usePersonal()

    useRedirectSome([!isAuthenticated, !isPersonalization], '/login')

    return (
        <div className={styles.personal}>
            <AuthPersonalTitle />
            <AuthPersonalDescription />
            <AuthPersonalContent />
        </div>
    )
}

export default PersonalPage
