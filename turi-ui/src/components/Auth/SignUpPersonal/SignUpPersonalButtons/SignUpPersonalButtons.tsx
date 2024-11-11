import { useTranslation } from 'react-i18next'
import { GreenButton } from '../../../Controls/Button'
import styles from './SignUpPersonalButtons.module.css'

interface Props {
    skipOnClick: () => void
    handleUpdateAccount: () => void
    error: string | null
}

const SignUpPersonalButtons = ({ skipOnClick, handleUpdateAccount, error }: Props) => {
    const { t } = useTranslation()

    return (
        <div className={styles.panel}>
            {error && <div className={styles.error}>{error}</div>}
            <div className={styles.buttons}>
                <GreenButton
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

export default SignUpPersonalButtons
