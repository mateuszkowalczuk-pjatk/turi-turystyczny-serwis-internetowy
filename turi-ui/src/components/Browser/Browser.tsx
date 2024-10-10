import Search from "./Search";
import TypeButtons from "./TypeButtons";
import styles from './Browser.module.css';

const Browser = () => {
    return (
        <div className={styles.browser}>
            <Search />
            <TypeButtons />
        </div>
    )
}

export default Browser;