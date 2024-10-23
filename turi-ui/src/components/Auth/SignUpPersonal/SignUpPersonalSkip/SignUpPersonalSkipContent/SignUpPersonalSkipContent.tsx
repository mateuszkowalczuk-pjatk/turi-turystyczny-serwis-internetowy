import SignUpPersonalSkipTitle from '../SignUpPersonalSkipTitle'
import SignUpPersonalSkipDescription from '../SignUpPersonalSkipDescription'
import SignUpPersonalSkipButtons from '../SignUpPersonalSkipButtons'
import styles from './SignUpPersonalSkipContent.module.css'

const SignUpPersonalSkipContent = ({ onClick }: { onClick: () => void }) => {
    return (
        <div className={styles.content}>
            <SignUpPersonalSkipTitle />
            <SignUpPersonalSkipDescription />
            <SignUpPersonalSkipButtons onClick={onClick} />
        </div>
    )
}

export default SignUpPersonalSkipContent
