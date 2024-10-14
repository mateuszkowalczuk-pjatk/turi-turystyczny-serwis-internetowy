import { useTranslation } from 'react-i18next'
import Input from '../../Controls/Input'
import styles from './RegistrationInputs.module.css'

const RegistrationInputs = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.inputs}>
            <Input
                placeholder={t('registration.login')}
            />
            <Input
                placeholder={t('registration.email')}
            />
            <Input
                placeholder={t('registration.password')}
            />
            <Input
                placeholder={t('registration.re-password')}
            />
        </div>
    )
}

export default RegistrationInputs;