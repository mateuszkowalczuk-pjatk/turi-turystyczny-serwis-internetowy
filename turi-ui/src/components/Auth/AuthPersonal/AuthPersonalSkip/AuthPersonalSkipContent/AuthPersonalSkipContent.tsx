import AuthPersonalSkipTitle from '../AuthPersonalSkipTitle'
import AuthPersonalSkipDescription from '../AuthPersonalSkipDescription'
import AuthPersonalSkipButtons from '../AuthPersonalSkipButtons'
import styles from './AuthPersonalSkipContent.module.css'

const AuthPersonalSkipContent = ({ onClick }: { onClick: () => void }) => {
    return (
        <div className={styles.content}>
            <AuthPersonalSkipTitle />
            <AuthPersonalSkipDescription />
            <AuthPersonalSkipButtons onClick={onClick} />
        </div>
    )
}

export default AuthPersonalSkipContent
