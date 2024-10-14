import { useTranslation } from 'react-i18next'
import TextRegular from '../../Text/TextRegular'
import styles from './RegistrationLogin.module.css'

const RegistrationLogin = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.login}>
            <TextRegular
              text={t('registration.signin')}
            />
        </div>
    )
}

export default RegistrationLogin;