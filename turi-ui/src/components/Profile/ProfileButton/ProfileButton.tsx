import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { GreenButton } from '../../Shared/Controls/Button'
import styles from './ProfileButton.module.css'

interface Props {
    handleSave: () => void
    error?: string | null
}

const ProfileButton = ({ handleSave, error }: Props) => {
    const { t } = useHooks()

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
