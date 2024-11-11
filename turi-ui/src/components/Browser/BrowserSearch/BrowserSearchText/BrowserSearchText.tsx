import TextBold from '../../../Controls/Text/TextBold'
import styles from './BrowserSearchText.module.css'

interface Props {
    text: string
    dash?: boolean
}

const BrowserSearchText = ({ text, dash = false }: Props) => {
    const className = dash ? `${styles.text} ${styles.dash}` : styles.text

    return (
        <div className={className}>
            <TextBold text={text} />
        </div>
    )
}

export default BrowserSearchText
