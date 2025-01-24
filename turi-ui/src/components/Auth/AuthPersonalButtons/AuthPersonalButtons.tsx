import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { GreenButton, GreyButton } from '../../Shared/Controls/Button'
import styles from './AuthPersonalButtons.module.css'

interface Props {
    skipOnClick: () => void
    handleUpdateAccount: () => void
    error: string | null
}

const AuthPersonalButtons = ({ skipOnClick, handleUpdateAccount, error }: Props) => {
    const { t } = useHooks()

    return (
        <div className={styles.panel}>
            {error && <div className={styles.error}>{error}</div>}
            <div className={styles.buttons}>
                <GreyButton
                    text={t('signup-personal.skip')}
                    onClick={skipOnClick}
                />
                <GreenButton
                    text={t('signup-personal.next')}
                    onClick={handleUpdateAccount}
                />
            </div>
        </div>
    )
}

export default AuthPersonalButtons
