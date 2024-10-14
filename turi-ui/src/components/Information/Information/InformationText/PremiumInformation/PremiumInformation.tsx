import { useTranslation } from 'react-i18next'
import TextRegular from '../../../../Text/TextRegular'
import styles from './PremiumInformation.module.css'

const PremiumInformation = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.premium}>
            <TextRegular
                text={t('information.premium-account-text')}
            />
        </div>
    )
}

export default PremiumInformation;