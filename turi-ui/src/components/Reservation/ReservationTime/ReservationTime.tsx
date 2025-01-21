import styles from './ReservationTime.module.css'
import TextRegular from '../../Shared/Controls/Text/TextRegular'

interface Props {
    title: string
    date: string
    hourFrom: string
    hourTo: string
}

const ReservationTime = ({ title, date, hourFrom, hourTo }: Props) => {
    return (
        <div className={styles.time}>
            <TextRegular text={title} />
            <TextRegular text={date} />
            <TextRegular text={hourFrom + '-' + hourTo} />
        </div>
    )
}

export default ReservationTime
