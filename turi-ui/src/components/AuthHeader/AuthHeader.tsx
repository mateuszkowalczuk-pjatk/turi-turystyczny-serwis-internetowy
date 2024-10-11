import Logo from '../Logo'
import styles from './AuthHeader.module.css'

const AuthHeader = () => {
    return (
        <div className={styles.header}>
            <Logo />
        </div>
    )
}

export default AuthHeader;