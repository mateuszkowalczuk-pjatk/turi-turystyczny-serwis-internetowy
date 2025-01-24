import { useStates } from '../../../hooks/shared/useStates.ts'
import { useRedirectEvery } from '../../../hooks/shared/useRedirect.ts'
import ProfileLanguage from '../../../components/Profile/ProfileLanguage'
import styles from './ProfilePreferencePage.module.css'

const ProfilePreferencePage = () => {
    const { isAuthenticated } = useStates()

    useRedirectEvery([!isAuthenticated], '/')

    return (
        <div className={styles.page}>
            <ProfileLanguage />
        </div>
    )
}

export default ProfilePreferencePage
