import { useTranslation } from 'react-i18next'
import TextMediumExtra from '../../Controls/Text/TextMediumExtra'
import styles from './ProfileTitle.module.css'

const ProfileTitle = () => {
    const { t } = useTranslation()

    return (
        <div className={styles.title}>
            <TextMediumExtra text={t('profile.title')} />
        </div>
    )
}

export default ProfileTitle
