import {FaRegCalendarAlt} from "react-icons/fa";
import styles from './Calendar.module.css';

const Calendar = () => {
    return (
        <div className={styles.calendar}>
            <FaRegCalendarAlt size={50} />
            data od - data do
        </div>
    )
}

export default Calendar;