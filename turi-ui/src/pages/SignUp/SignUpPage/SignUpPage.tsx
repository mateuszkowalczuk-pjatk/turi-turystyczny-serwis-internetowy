import RegistrationHeader from '../../../components/Registration/RegistrationHeader'
import RegistrationInputs from '../../../components/Registration/RegistrationInputs'
import RegistrationButton from '../../../components/Registration/RegistrationButton'
import RegistrationLogin from '../../../components/Registration/RegistrationLogin'
import styles from '../../../components/Registration/Registration.module.css'

const SignUpPage = () => {
    return (
      //Z registration component na signup
        <div className={styles.panel}>
            <RegistrationHeader />
            <RegistrationInputs />
            <RegistrationButton />
            <RegistrationLogin />
        </div>
    )
}

export default SignUpPage;