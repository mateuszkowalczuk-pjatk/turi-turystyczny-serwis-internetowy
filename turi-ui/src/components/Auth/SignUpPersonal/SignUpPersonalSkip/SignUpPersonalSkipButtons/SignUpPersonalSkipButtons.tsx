import { useTranslation } from 'react-i18next'
import { GreyButton } from '../../../../Controls/Button'
import styles from './SignUpPersonalSkipButtons.module.css'

const SignUpPersonalSkipButtons = ({ onClick }: { onClick: () => void }) => {
    const { t } = useTranslation();

    return (
        <div className={styles.buttons}>
            <GreyButton
                text={t('signup-personal.back')}
                onClick={onClick}
            />
            <GreyButton
                text={t('signup-personal.continue')}
            />
        </div>
    )
}

export default SignUpPersonalSkipButtons;