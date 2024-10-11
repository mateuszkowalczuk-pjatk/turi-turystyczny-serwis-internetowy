import { useTranslation } from 'react-i18next'
import TextMediumExtra from '../../../Text/TextMediumExtra'
import styles from './RegistrationHeader.module.css'

const RegistrationHeader = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.header}>
            <TextMediumExtra
                text={t('registration.header')}
            />
        </div>
    )
}

export default RegistrationHeader;