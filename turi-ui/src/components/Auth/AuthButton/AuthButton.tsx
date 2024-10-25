import { GreenButton } from '../../Controls/Button'
import styles from './AuthButton.module.css'

interface Props {
    text: string
    onClick?: () => void
    type?: 'button' | 'submit' | 'reset'
}

const AuthButton = ({ text, onClick, type }: Props) => {
    return (
        <div className={styles.button}>
            <GreenButton
                text={text}
                onClick={onClick}
                type={type}
            />
        </div>
    )
}

export default AuthButton
