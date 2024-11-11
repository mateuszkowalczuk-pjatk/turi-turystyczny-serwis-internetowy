import SignUpPersonalSkipContent from '../SignUpPersonalSkipContent'
import styles from './SignUpPersonalSkipOverlay.module.css'

const SignUpPersonalSkipOverlay = ({ onClick }: { onClick: () => void }) => {
    return (
        <div className={styles.overlay}>
            <SignUpPersonalSkipContent onClick={onClick} />
        </div>
    )
}

export default SignUpPersonalSkipOverlay
