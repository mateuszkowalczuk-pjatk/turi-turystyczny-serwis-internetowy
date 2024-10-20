import { useTranslation } from 'react-i18next'
import { useNavigate } from 'react-router-dom'
import SignUpPanel from '../../../components/Auth/SignUpPanel'
import AuthTitle from '../../../components/Auth/AuthTitle'
import AuthInput from '../../../components/Auth/AuthInput'
import AuthButton from '../../../components/Auth/AuthButton'
import AuthTopLink from '../../../components/Auth/AuthTopLink'
import AuthDownLink from '../../../components/Auth/AuthDownLink'

const SignUpPage = () => {
    const { t } = useTranslation();
    const navigate = useNavigate();

    const navigateToVerify = () => {
        navigate('/signup/verify');
    }

    const navigateToLogin = () => {
        navigate('/login');
    }

    return (
        <SignUpPanel
            header={
                <AuthTitle
                    text={t('signup.title')}
                />
            }
            login={
                <AuthInput
                    text={t('signup.login')}
                />
            }
            email={
                <AuthInput
                    text={t('signup.email')}
                />
            }
            password={
                <AuthInput
                    text={t('signup.password')}
                />
            }
            rePassword={
                <AuthInput
                    text={t('signup.re-password')}
                />
            }
            button={
                <AuthButton
                    text={t('signup.button')}
                    onClick={navigateToVerify}
                />
            }
            top={ <AuthTopLink /> }
            down={
                <AuthDownLink
                    firstLink={t('signup.signin')}
                    secondLink={'right'}
                    firstOnClick={navigateToLogin}
                />
            }
        />
    )
}

export default SignUpPage