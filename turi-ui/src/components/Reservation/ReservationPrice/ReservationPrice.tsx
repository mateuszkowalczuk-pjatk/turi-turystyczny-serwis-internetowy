import { useHooks } from '../../../hooks/shared/useHooks.ts'
import TextMedium from '../../Shared/Controls/Text/TextMedium'
import TextRegular from '../../Shared/Controls/Text/TextRegular'
import styles from './ReservationPrice.module.css'

const ReservationPrice = ({ price }: { price: number }) => {
    const { t } = useHooks()

    return (
        <div className={styles.price}>
            <TextMedium
                text={t('reservation.reservation-total-price') + price + t('reservation.reservation-currency')}
            />
            <TextRegular text={t('reservation.reservation-taxes')} />
        </div>
    )
}

export default ReservationPrice
