import TextRegular from '../../Controls/Text/TextRegular'
import styles from './InformationText.module.css'

const InformationText = ({ text }: { text: string }) => {
    return (
        <div className={styles.text}>
            <TextRegular
                text={ text }
            />
        </div>
    )
}

export default InformationText;