import BrowserSearchDate from '../BrowserSearchDate'
import BrowserSearchPanel from '../BrowserSearchPanel'
import styles from './BrowserSearchContent.module.css'

const BrowserSearchContent = () => {
    return (
        <div className={styles.search}>
            <BrowserSearchDate />
            <BrowserSearchPanel />
        </div>
    )
}

export default BrowserSearchContent