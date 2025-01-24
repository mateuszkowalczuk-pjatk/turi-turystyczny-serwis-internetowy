import { useHooks } from '../../../hooks/shared/useHooks.ts'
import TextMedium from '../../Shared/Controls/Text/TextMedium'
import TextRegular from '../../Shared/Controls/Text/TextRegular'
import PremiumOfferList from '../PremiumOfferList'
import styles from './PremiumOfferComp.module.css'

interface Props {
    text: string
    list: boolean
    length?: number
    price?: number
}

const PremiumOfferComp = ({ text, list, length, price }: Props) => {
    const { t } = useHooks()

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

export default PremiumOfferComp
