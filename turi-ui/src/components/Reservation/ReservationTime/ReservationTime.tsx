import { handleDateDisplay, handleTimeDisplay } from '../../../utils/handleDateTimeDisplay.ts'
import TextRegular from '../../Shared/Controls/Text/TextRegular'
import styles from './ReservationTime.module.css'
import TextExtraLight from '../../Shared/Controls/Text/TextExtraLight'

interface Props {
    title: string
    date: string
    hourFrom: string
    hourTo: string
}

const ReservationTime = ({ title, date, hourFrom, hourTo }: Props) => {
    return (
        <div className={styles.time}>
            <TextExtraLight text={title} />
            <TextRegular text={handleDateDisplay(date)} />
            <TextRegular text={`${handleTimeDisplay(hourFrom)} - ${handleTimeDisplay(hourTo)}`} />{' '}
        </div>
    )
}

export default ReservationTime
