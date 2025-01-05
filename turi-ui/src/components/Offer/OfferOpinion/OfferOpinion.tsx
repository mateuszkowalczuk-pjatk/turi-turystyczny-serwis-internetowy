import { useTranslation } from 'react-i18next'
import TextRegular from '../../Shared/Controls/Text/TextRegular'
import styles from './OfferOpinion.module.css'

const OfferOpinion = () => {
    const { t } = useTranslation()
    return (
        <div className={styles.opinion}>
            <div className={styles.title}>
                <TextRegular text={t('offer.opinion')} />
            </div>
            <div className={styles.content}>
                <div className={styles.panel}>
                    <TextRegular text={t('offer.opinion-not-found')} />
                </div>
            </div>
        </div>
    )
}

export default OfferOpinion
