import SignUpPersonalTitle from '../../../components/Auth/SignUpPersonal/SignUpPersonalTitle'
import SignUpPersonalDescription from '../../../components/Auth/SignUpPersonal/SignUpPersonalDescription'
import SignUpPersonalContent from '../../../components/Auth/SignUpPersonal/SignUpPersonalContent'
import styles from './SignUpPersonalPage.module.css'

const SignUpPersonalPage = () => {
    return (
        <div className={styles.personal}>
            <SignUpPersonalTitle />
            <SignUpPersonalDescription />
            <SignUpPersonalContent />
        </div>
    )
}

export default SignUpPersonalPage