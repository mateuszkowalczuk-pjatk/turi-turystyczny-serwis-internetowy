import { useTranslation } from 'react-i18next'
import styles from './ProfileTitle.module.css'

const ProfileTitle = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.title}>
            {t('profile.title')}
        </div>
    )
}

export default ProfileTitle;