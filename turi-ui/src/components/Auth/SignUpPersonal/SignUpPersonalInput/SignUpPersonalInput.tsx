import Input from '../../../Controls/Input'
import styles from './SignUpPersonalInput.module.css'

const SignUpPersonalInput = ({ text }: { text: string }) => {
    return (
        <div className={styles.input}>
            <Input
                placeholder={ text }
            />
        </div>
    )
}

export default SignUpPersonalInput;