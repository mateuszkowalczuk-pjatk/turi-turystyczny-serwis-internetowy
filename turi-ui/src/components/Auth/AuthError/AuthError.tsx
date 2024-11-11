import styles from './AuthError.module.css'
import TextRegular from '../../Controls/Text/TextRegular'

const AuthError = ({ error }: { error: string }) => {
    return (
        <div className={styles.error}>
            <TextRegular text={error} />
        </div>
    )
}

export default AuthError
