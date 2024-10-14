import { useTranslation } from 'react-i18next'
import TextMedium from '../../../../Text/TextMedium'
import styles from './HeaderInformation.module.css'

const HeaderInformation = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.header}>
            <TextMedium
                text={t('information.header-text')}
            />
        </div>
    )
}

export default HeaderInformation;