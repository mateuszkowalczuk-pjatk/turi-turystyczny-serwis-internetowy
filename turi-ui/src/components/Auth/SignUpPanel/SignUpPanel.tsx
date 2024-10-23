import { useTranslation } from 'react-i18next'
import { useNavigate } from 'react-router-dom'
import AuthTitle from '../AuthTitle'
import AuthInput from '../AuthInput'
import AuthButton from '../AuthButton'
import AuthTopLink from '../AuthTopLink'
import AuthDownLink from '../AuthDownLink'
import styles from './SignUpPanel.module.css'

const SignUpPanel = () => {
    const { t } = useTranslation()
    const navigate = useNavigate()

    const navigateToVerify = () => {
        navigate('/signup/verify')
    }

    const navigateToLogin = () => {
        navigate('/login')
    }

    return (
        <div className={styles.panel}>
            <AuthTitle text={t('signup.title')} />
            <AuthInput text={t('signup.login')} />
            <AuthInput text={t('signup.email')} />
            <AuthInput text={t('signup.password')} />
            <AuthInput text={t('signup.re-password')} />
            <AuthButton
                text={t('signup.button')}
                onClick={navigateToVerify}
            />
            <AuthTopLink />
            <AuthDownLink
                firstLink={t('signup.signin')}
                secondLink={'right'}
                firstOnClick={navigateToLogin}
            />
        </div>
    )
}

export default SignUpPanel
