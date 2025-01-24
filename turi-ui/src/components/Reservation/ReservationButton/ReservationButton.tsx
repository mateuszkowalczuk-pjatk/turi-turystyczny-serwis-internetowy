import { GreenButton } from '../../Shared/Controls/Button'
import styles from './ReservationButton.module.css'

interface Props {
    text?: string
    onlyDisplay?: boolean
}

const ReservationButton = ({ text, onlyDisplay }: Props) => {
    return (
        <div className={styles.button}>
            {!onlyDisplay && text && (
                <GreenButton
                    text={text}
                    type={'submit'}
                />
            )}
        </div>
    )
}

export default ReservationButton
