import SignUpPersonalSkipContent from './SignUpPersonalSkipContent'
import styles from './SignUpPersonalSkip.module.css'

const SignUpPersonalSkip = ({ onClick }: { onClick: () => void }) => {
    return (
        <div className={styles.overlay}>
            <SignUpPersonalSkipContent
                onClick={ onClick }
            />
        </div>
    )
}

export default SignUpPersonalSkip;