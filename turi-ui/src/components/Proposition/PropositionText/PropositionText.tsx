import TextMedium from '../../Controls/Text/TextMedium'
import styles from './PropositionText.module.css'

const PropositionText = ({ text }: { text: string }) => {
    return (
        <div className={styles.text}>
            <TextMedium text={text} />
        </div>
    )
}

export default PropositionText
