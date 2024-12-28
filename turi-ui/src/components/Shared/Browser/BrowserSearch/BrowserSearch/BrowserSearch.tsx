import BrowserSearchDate from '../BrowserSearchDate'
import BrowserSearchPanel from '../BrowserSearchPanel'
import styles from './BrowserSearch.module.css'

const BrowserSearch = () => {
    return (
        <div className={styles.search}>
            <BrowserSearchDate />
            <BrowserSearchPanel />
        </div>
    )
}

export default BrowserSearch
