import TextMediumExtra from '../../Controls/Text/TextMediumExtra'
import styles from './AuthTitle.module.css'

const AuthTitle = ({ text }: { text: string }) => {
    return (
        <div className={styles.title}>
            <TextMediumExtra
                text={ text }
            />
        </div>
    )
}

export default AuthTitle