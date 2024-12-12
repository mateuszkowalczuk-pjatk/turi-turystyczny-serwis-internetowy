import TextRegular from '../../Controls/Text/TextRegular'
import styles from './PremiumDescription.module.css'

const PremiumDescription = ({ text }: { text: string }) => {
    return (
        <div className={styles.description}>
            <TextRegular text={text} />
        </div>
    )
}

export default PremiumDescription
