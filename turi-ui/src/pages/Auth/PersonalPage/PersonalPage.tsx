import { useSelector } from 'react-redux'
import { RootState } from '../../../store/store.ts'
import { useRedirectSome } from '../../../hooks/useRedirect.ts'
import AuthPersonalTitle from '../../../components/Auth/AuthPersonal/AuthPersonalTitle'
import AuthPersonalDescription from '../../../components/Auth/AuthPersonal/AuthPersonalDescription'
import AuthPersonalContent from '../../../components/Auth/AuthPersonal/AuthPersonalContent'
import styles from './PersonalPage.module.css'

const PersonalPage = () => {
    const isAuthenticated = useSelector((state: RootState) => state.auth.isAuthenticated)
    const isPersonalization = useSelector((state: RootState) => state.personal.isPersonalization)

    useRedirectSome([!isAuthenticated, !isPersonalization], '/login')

    return (
        // tutaj moze comopnent z shared który jest też wykorzystywany z profilu, ten główny panel z borderem
        <div className={styles.personal}>
            <AuthPersonalTitle />
            <AuthPersonalDescription />
            <AuthPersonalContent />
        </div>
    )
}

export default PersonalPage
