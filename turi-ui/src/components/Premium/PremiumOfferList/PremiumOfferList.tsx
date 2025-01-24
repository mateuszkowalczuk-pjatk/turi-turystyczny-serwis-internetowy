import { useHooks } from '../../../hooks/shared/useHooks.ts'
import TextRegular from '../../Shared/Controls/Text/TextRegular'
import styles from './PremiumOfferList.module.css'

const PremiumOfferList = () => {
    const { t } = useHooks()

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
