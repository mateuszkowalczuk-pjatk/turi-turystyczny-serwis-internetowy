import TextRegular from "../../Controls/Text/TextRegular";
import styles from './AuthTopLink.module.css'

const AuthTopLink = ({ text }: { text?: string }) => {
    return (
        <div className={styles.link}>
            {text && (
                <TextRegular
                    text={text}
                />
            )}
        </div>
    )
}

export default AuthTopLink;