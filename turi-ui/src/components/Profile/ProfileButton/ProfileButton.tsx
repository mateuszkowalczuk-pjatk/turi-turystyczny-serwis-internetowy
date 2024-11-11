import { useTranslation } from 'react-i18next'
import { GreenButton } from '../../Controls/Button'
import styles from './ProfileButton.module.css'

interface Props {
    handleSave: () => void
    error?: string | null
}

const ProfileButton = ({ handleSave, error }: Props) => {
    const { t } = useTranslation()

    return (
        <div className={styles.button}>
            {error && <div className={styles.error}>{error}</div>}
            <GreenButton
                text={t('profile.save')}
                onClick={handleSave}
            />
        </div>
    )
}

export default ProfileButton
