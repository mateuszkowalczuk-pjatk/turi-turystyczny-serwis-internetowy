import BrowserSearchContent from '../BrowserSearch/BrowserSearchContent'
import BrowserTypeButtons from '../BrowserTypeButtons'
import styles from './BrowserContent.module.css'

const BrowserContent = () => {
    return (
        <div className={styles.browser}>
            <BrowserSearchContent />
            <BrowserTypeButtons />
        </div>
    )
}

export default BrowserContent
