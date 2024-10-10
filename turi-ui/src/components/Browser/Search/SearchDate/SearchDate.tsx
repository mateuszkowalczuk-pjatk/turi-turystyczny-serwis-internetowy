import SearchCalendar from "./SearchCalendar";
import TextSearchDate from "./TextSearchDate";
import styles from './SearchDate.module.css'

const SearchDate = () => {
    return (
        <div className={styles.date}>
            <SearchCalendar />
            <TextSearchDate />
        </div>
    )
}

export default SearchDate;