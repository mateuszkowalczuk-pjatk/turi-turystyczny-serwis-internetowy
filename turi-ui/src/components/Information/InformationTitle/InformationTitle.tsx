import TextMedium from '../../Controls/Text/TextMedium'
import styles from './InformationTitle.module.css'

const InformationTitle = ({ text }: { text: string }) => {
    return (
        <div className={styles.header}>
            <TextMedium
                text={ text }
            />
        </div>
    )
}

export default InformationTitle