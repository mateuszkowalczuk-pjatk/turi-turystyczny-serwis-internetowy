import TextRegular from '../../Controls/Text/TextRegular'
import styles from './AuthTopLink.module.css'

interface Props {
    text?: string;
    onClick?: () => void;
}

const AuthTopLink = ({ text, onClick }: Props) => {
    return (
        <div className={styles.link}>
            {text && (
                <TextRegular
                    text={text}
                    onClick={onClick}
                />
            )}
        </div>
    )
}

export default AuthTopLink