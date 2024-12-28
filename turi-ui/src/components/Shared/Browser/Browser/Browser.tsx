import BrowserSearch from '../BrowserSearch/BrowserSearch'
import BrowserTypeButtons from '../BrowserTypeButtons'
import styles from './Browser.module.css'

const Browser = () => {
    return (
        <div className={styles.browser}>
            <BrowserSearch />
            <BrowserTypeButtons />
        </div>
    )
}

export default Browser
