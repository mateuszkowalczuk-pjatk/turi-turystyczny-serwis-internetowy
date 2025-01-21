import { GreenButton } from '../../Shared/Controls/Button'
import styles from './ReservationButton.module.css'

const ReservationButton = ({ text }: { text: string }) => {
    return (
        <div className={styles.button}>
            <GreenButton
                text={text}
                type={'submit'}
            />
        </div>
    )
}

export default ReservationButton
