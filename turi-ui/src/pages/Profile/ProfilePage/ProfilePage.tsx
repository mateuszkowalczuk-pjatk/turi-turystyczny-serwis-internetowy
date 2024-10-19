import { useTranslation } from "react-i18next";
import ProfileModule from '../../../components/Profile/ProfileModule'
import styles from './ProfilePage.module.css'

const ProfilePage = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.page}>
            <ProfileModule
                text={t('profile.login')}
            />
            <ProfileModule
                text={t('profile.email')}
            />
            <ProfileModule
                text={t('profile.password')}
            />
            <ProfileModule
                text={t('profile.re-password')}
            />
        </div>
    )
}

export default ProfilePage;