import AuthPersonalSkipTitle from '../AuthPersonalSkipTitle'
import AuthPersonalSkipButtons from '../AuthPersonalSkipButtons'
import AuthPersonalSkipDescription from '../AuthPersonalSkipDescription'
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
