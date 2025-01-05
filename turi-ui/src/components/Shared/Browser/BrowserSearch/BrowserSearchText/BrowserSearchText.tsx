import TextBold from '../../../Controls/Text/TextBold'
import styles from './BrowserSearchText.module.css'

const BrowserSearchText = ({ text }: { text: string }) => {
    return (
        <div className={styles.text}>
            <TextBold text={text} />
        </div>
    )
}

export default BrowserSearchText
