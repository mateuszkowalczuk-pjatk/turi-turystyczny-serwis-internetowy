import AuthPersonalSkipContent from '../AuthPersonalSkipContent'
import styles from './AuthPersonalSkipOverlay.module.css'

const AuthPersonalSkipOverlay = ({ onClick }: { onClick: () => void }) => {
    return (
        <div className={styles.overlay}>
            <AuthPersonalSkipContent onClick={onClick} />
        </div>
    )
}

export default AuthPersonalSkipOverlay
