import { useTranslation } from 'react-i18next'
import TextMedium from '../../Shared/Controls/Text/TextMedium'
import PremiumOfferList from '../PremiumOfferList'
import TextRegular from '../../Shared/Controls/Text/TextRegular'
import styles from './PremiumOffer.module.css'

interface Props {
    text: string
    list: boolean
    length?: number
    price?: number
}

const PremiumOffer = ({ text, list, length, price }: Props) => {
    const { t } = useTranslation()

    return (
        <div className={styles.offer}>
            <TextMedium text={text} />
            {list ? (
                <PremiumOfferList />
            ) : (
                <div className={styles.price}>
                    <TextRegular text={t('premium.offer-length', { length: length })} />
                    <TextRegular text={t('premium.offer-price', { price: price })} />
                </div>
            )}
        </div>
    )
}

export default PremiumOffer
