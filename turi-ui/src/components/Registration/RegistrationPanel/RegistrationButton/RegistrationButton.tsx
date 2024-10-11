import { useTranslation } from 'react-i18next'
import { GreenButton } from '../../../Button'
import styles from './RegistrationButton.module.css'

const RegistrationButton = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.button}>
            <GreenButton
                text={t('registration.signup')}
            />
        </div>
    )
}

export default RegistrationButton;