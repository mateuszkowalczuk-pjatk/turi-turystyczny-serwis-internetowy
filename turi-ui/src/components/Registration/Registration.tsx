import RegistrationHeader from './RegistrationHeader'
import RegistrationInputs from './RegistrationInputs'
import RegistrationButton from './RegistrationButton'
import RegistrationLogin from './RegistrationLogin'
import styles from './Registration.module.css'

const Registration = () => {
    return (
        <div className={styles.panel}>
            <RegistrationHeader />
            <RegistrationInputs />
            <RegistrationButton />
            <RegistrationLogin />
        </div>
    )
}

export default Registration;