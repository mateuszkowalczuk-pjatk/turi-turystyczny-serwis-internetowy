import TextMedium from '../../Shared/Controls/Text/TextMedium'
import styles from './InfoTitle.module.css'

const InfoTitle = ({ text }: { text: string }) => {
    return (
        <div className={styles.title}>
            <TextMedium text={text} />
        </div>
    )
}

export default InfoTitle
