import { handleDateDisplay, handleTimeDisplay } from '../../../utils/handleDateTimeDisplay.ts'
import TextMedium from '../../Shared/Controls/Text/TextMedium'
import TextRegular from '../../Shared/Controls/Text/TextRegular'
import TextExtraLight from '../../Shared/Controls/Text/TextExtraLight'
import styles from './ReservationPlanDate.module.css'

interface Props {
    text: string
    date: Date
    hourFrom: string
    hourTo: string
}

const ReservationPlanDate = ({ text, date, hourFrom, hourTo }: Props) => {
    return (
        <div className={styles.date}>
            <div className={styles.time}>
                <TextExtraLight text={handleDateDisplay(date.toString())} />
                <TextRegular text={handleTimeDisplay(hourFrom) + ' - ' + handleTimeDisplay(hourTo)} />
            </div>
            <div className={styles.name}>
                <TextMedium text={text} />
            </div>
        </div>
    )
}

export default ReservationPlanDate
