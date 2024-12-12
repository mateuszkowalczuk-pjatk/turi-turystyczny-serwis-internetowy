import { useTranslation } from 'react-i18next'
import TextRegular from '../../Controls/Text/TextRegular'
import styles from './PremiumOfferList.module.css'

const PremiumOfferList = () => {
    const { t } = useTranslation()

    return (
        <div className={styles.list}>
            <ul className={styles.offer}>
                <li>
                    <TextRegular text={t('premium.offer-list-first-time')} />
                </li>
                <li>
                    <TextRegular text={t('premium.offer-list-second-time')} />
                </li>
                <li>
                    <TextRegular text={t('premium.offer-list-third-time')} />
                </li>
                <li>
                    <TextRegular text={t('premium.offer-list-fourth-time')} />
                </li>
            </ul>
        </div>
    )
}

export default PremiumOfferList
