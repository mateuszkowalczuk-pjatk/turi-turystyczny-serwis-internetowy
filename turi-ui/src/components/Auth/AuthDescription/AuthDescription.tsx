import TextRegular from '../../Controls/Text/TextRegular'
import styles from './AuthDescription.module.css'

const AuthDescription = ({ text }: { text: string }) => {
    return (
        <div className={styles.description}>
            <TextRegular text={text} />
        </div>
    )
}

export default AuthDescription
