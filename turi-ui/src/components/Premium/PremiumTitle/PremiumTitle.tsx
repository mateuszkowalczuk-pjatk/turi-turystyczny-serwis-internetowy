import { useTranslation } from 'react-i18next'
import TextMediumExtra from '../../Controls/Text/TextMediumExtra'
import styles from './PremiumTitle.module.css'

const PremiumTitle = () => {
    const { t } = useTranslation()

    return (
        <div className={styles.title}>
            <TextMediumExtra text={t('premium.title')} />
        </div>
    )
}

export default PremiumTitle
