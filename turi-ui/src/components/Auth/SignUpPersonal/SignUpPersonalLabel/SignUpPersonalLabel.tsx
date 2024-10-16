import TextMedium from '../../../Controls/Text/TextMedium'
import styles from './SignUpPersonalLabel.module.css'

const SignUpPersonalLabel = ({ text }: { text: string }) => {
    return (
        <div className={styles.label}>
            <TextMedium
                text={ text }
            />
        </div>
    )
}

export default SignUpPersonalLabel;