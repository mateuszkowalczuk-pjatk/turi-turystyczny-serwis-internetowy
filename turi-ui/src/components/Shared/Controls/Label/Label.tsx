import TextRegular from '../Text/TextRegular'
import styles from './Label.module.css'

const Label = ({ text }: { text: string }) => {
    return (
        <div className={styles.label}>
            <TextRegular text={text} />
        </div>
    )
}

export default Label
