import { useRedirectSome } from '../../../hooks/useRedirect.ts'
import { useStates } from '../../../hooks/useStates.ts'
import AuthPersonalTitle from '../../../components/Auth/AuthPersonal/AuthPersonalTitle'
import AuthPersonalDescription from '../../../components/Auth/AuthPersonal/AuthPersonalDescription'
import AuthPersonalContent from '../../../components/Auth/AuthPersonal/AuthPersonalContent'
import styles from './PersonalPage.module.css'

const PersonalPage = () => {
    const { isAuthenticated, isPersonal } = useStates()

    useRedirectSome([!isAuthenticated, !isPersonal], '/login')

    return (
        <div className={styles.personal}>
            <AuthPersonalTitle />
            <AuthPersonalDescription />
            <AuthPersonalContent />
        </div>
    )
}

export default PersonalPage
