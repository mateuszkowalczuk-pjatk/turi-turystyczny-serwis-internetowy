import { useNavigate } from 'react-router-dom'
import { useTranslation } from 'react-i18next'
import AuthPanel from '../../../components/Auth/AuthPanel'
import AuthTitle from '../../../components/Auth/AuthTitle'
import AuthInput from '../../../components/Auth/AuthInput'
import AuthButton from '../../../components/Auth/AuthButton'
import AuthTopLink from '../../../components/Auth/AuthTopLink'
import AuthDownLink from '../../../components/Auth/AuthDownLink'

const LoginPage = () => {
    const { t } = useTranslation()
    const navigate = useNavigate()

    const navigateToHome = () => {
        navigate('/')
    }

    const loginByGoogleAccount = () => {
        console.log('Google account')
    }

    const navigateToCheck = () => {
        navigate('/login/check')
    }

    const navigateToSignUp = () => {
        navigate('/signup')
    }

    return (
        <AuthPanel
            header={<AuthTitle text={t('login.title')} />}
            option={<AuthInput placeholder={t('login.login')} />}
            input={<AuthInput placeholder={t('login.password')} />}
            button={
                <AuthButton
                    text={t('login.button')}
                    onClick={navigateToHome}
                />
            }
            top={
                <AuthTopLink
                    text={t('login.google')}
                    onClick={loginByGoogleAccount}
                />
            }
            down={
                <AuthDownLink
                    firstLink={t('login.reset')}
                    secondLink={t('login.signup')}
                    firstOnClick={navigateToCheck}
                    secondOnClick={navigateToSignUp}
                />
            }
        />
    )
}

export default LoginPage
