import { GreenButton } from '../../Shared/Controls/Button'
import styles from './AuthButton.module.css'

interface Props {
    text: string
    onClick?: () => void
    type?: 'button' | 'submit' | 'reset'
    disabled?: boolean
}

const AuthButton = ({ text, onClick, type, disabled }: Props) => {
    return (
        <div className={styles.button}>
            <GreenButton
                text={text}
                onClick={onClick}
                type={type}
                disabled={disabled}
            />
        </div>
    )
}

export default AuthButton
