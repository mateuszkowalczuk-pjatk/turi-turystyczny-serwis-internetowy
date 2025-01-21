import TextRegular from '../../Shared/Controls/Text/TextRegular'
import TextExtraLight from '../../Shared/Controls/Text/TextExtraLight'
import styles from './ReservationPrice.module.css'

const ReservationPrice = ({ price }: { price: number }) => {
    return (
        <div className={styles.price}>
            <TextRegular text={'Łączna kwota: ' + price + ' zł'} />
            <TextExtraLight text={'W tym podatki i inne opłaty'} />
        </div>
    )
}

export default ReservationPrice
