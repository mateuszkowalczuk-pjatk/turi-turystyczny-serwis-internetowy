import TextRegular from '../../Shared/Controls/Text/TextRegular'
import styles from './AuthError.module.css'

const AuthError = ({ error }: { error: string }) => {
    return (
        <div className={styles.error}>
            <TextRegular text={error} />
        </div>
    )
}

export default AuthError
