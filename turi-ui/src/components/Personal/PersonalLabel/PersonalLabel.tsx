import TextRegular from '../../Controls/Text/TextRegular'
import styles from './PersonalLabel.module.css'

const PersonalLabel = ({ text }: { text: string }) => {
    return (
        <div className={styles.label}>
            <TextRegular text={text} />
        </div>
    )
}

export default PersonalLabel
