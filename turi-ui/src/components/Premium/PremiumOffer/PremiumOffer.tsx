import styles from './PremiumOffer.module.css'
import TextMedium from '../../Controls/Text/TextMedium'
import PremiumOfferList from '../PremiumOfferList'
import TextRegular from '../../Controls/Text/TextRegular'
import { useTranslation } from 'react-i18next'

interface Props {
    text: string
    list: boolean
    months?: number
    price?: number
}

const PremiumOffer = ({ text, list, months, price }: Props) => {
    const { t } = useTranslation()

    return (
        <div className={styles.offer}>
            <TextMedium text={text} />
            {list ? (
                <PremiumOfferList />
            ) : (
                <div className={styles.price}>
                    <TextRegular text={t('premium.offer-access', { months: months })} />
                    <TextRegular text={t('premium.offer-price', { price: price })} />
                </div>
            )}
        </div>
    )
}

export default PremiumOffer
