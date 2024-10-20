import Calendar from '../../../../Controls/Calendar'
import styles from './SearchCalendar.module.css'

const SearchCalendar = () => {
    function handleOnClick() {}

    return (
        <div className={styles.calendar} onClick={handleOnClick} role="button" tabIndex={0}>
            <Calendar />
        </div>
    )
}

export default SearchCalendar