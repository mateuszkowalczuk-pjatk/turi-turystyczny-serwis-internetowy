import { useTranslation } from 'react-i18next'
import Input from '../../../Input'
import styles from './RegistrationInputs.module.css';

const RegistrationInputs = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.inputs}>
            <Input
                text={t('registration.login')}
            />
            <Input
                text={t('registration.email')}
            />
            <Input
                text={t('registration.password')}
            />
            <Input
                text={t('registration.re-password')}
            />
        </div>
    )
}

export default RegistrationInputs;