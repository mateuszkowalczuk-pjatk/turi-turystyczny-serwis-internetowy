import BrowserSearchIcon from '../BrowserSearchIcon'
import BrowserSearchInput from '../BrowserSearchInput'
import BrowserSearchButton from '../BrowserSearchButton'
import styles from './BrowserSearchPanel.module.css'

const BrowserSearchPanel = () => {
    return (
        <div className={styles.panel}>
            <BrowserSearchIcon />
            <BrowserSearchInput />
            <BrowserSearchButton />
        </div>
    )
}

export default BrowserSearchPanel