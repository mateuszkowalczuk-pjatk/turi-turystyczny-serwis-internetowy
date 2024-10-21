import { GreenButton } from '../../Controls/Button'
import styles from './AuthButton.module.css'

interface Props {
    text: string;
    onClick: () => void;
}

const AuthButton = ({ text, onClick }: Props) => {
    return (
        <div className={styles.button}>
            <GreenButton
                text={ text }
                onClick={ onClick }
            />
        </div>
    )
}

export default AuthButton