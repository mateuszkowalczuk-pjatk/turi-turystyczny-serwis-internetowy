import TextRegular from '../../../Controls/Text/TextRegular'
import styles from './SignUpPersonalLabel.module.css'

const SignUpPersonalLabel = ({ text }: { text: string }) => {
    return (
        <div className={styles.label}>
            <TextRegular
                text={ text }
            />
        </div>
    )
}

export default SignUpPersonalLabel;