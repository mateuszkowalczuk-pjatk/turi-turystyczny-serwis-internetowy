import Input from '../../Controls/Input'
import styles from './AuthInput.module.css'

const AuthInput = ({ text }: { text: string }) => {
    return (
        <div className={styles.input}>
            <Input
                placeholder={ text }
            />
        </div>
    )
}

export default AuthInput;