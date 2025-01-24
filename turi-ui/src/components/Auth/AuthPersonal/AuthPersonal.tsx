import AuthPersonalTitle from '../AuthPersonalTitle'
import AuthPersonalPanel from '../AuthPersonalPanel'
import AuthPersonalDescription from '../AuthPersonalDescription'
import styles from './AuthPersonal.module.css'

const AuthPersonal = () => {
    return (
        <div className={styles.personal}>
            <AuthPersonalTitle />
            <AuthPersonalDescription />
            <AuthPersonalPanel />
        </div>
    )
}

export default AuthPersonal
