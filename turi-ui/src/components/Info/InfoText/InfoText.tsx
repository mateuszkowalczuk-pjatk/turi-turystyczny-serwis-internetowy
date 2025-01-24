import TextRegular from '../../Shared/Controls/Text/TextRegular'
import styles from './InfoText.module.css'

const InfoText = ({ text }: { text: string }) => {
    return (
        <div className={styles.text}>
            <TextRegular text={text} />
        </div>
    )
}

export default InfoText
