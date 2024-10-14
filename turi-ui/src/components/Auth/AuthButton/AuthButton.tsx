import { GreenButton } from '../../Controls/Button'
import styles from './AuthButton.module.css'

const AuthButton = ({ text }: { text: string }) => {
    return (
        <div className={styles.button}>
            <GreenButton
                text={ text }
            />
        </div>
    )
}

export default AuthButton;