import { useTranslation } from 'react-i18next'
import { useNavigate } from 'react-router-dom'
import AuthPanel from '../../../components/Auth/AuthPanel'
import AuthTitle from '../../../components/Auth/AuthTitle'
import AuthDescription from '../../../components/Auth/AuthDescription'
import AuthInput from '../../../components/Auth/AuthInput'
import AuthButton from '../../../components/Auth/AuthButton'
import AuthTopLink from '../../../components/Auth/AuthTopLink'
import AuthDownLink from '../../../components/Auth/AuthDownLink'

const LoginPasswordCodePage = () => {
    const { t } = useTranslation();
    const navigate = useNavigate();

    const navigateToReset = () => {
        navigate('/login/reset')
    }

    const sendResetCodeAgain = () => {
        console.log('Code')
    }

    const navigateToLogin = () => {
        navigate('/login')
    }

    return (
        <AuthPanel
            header={
                <AuthTitle
                    text={t('login-code.title')}
                />
            }
            option={
                <AuthDescription
                    text={t('login-code.description')}
                />
            }
            input={
                <AuthInput
                    text={t('login-code.code')}
                />
            }
            button={
                <AuthButton
                    text={t('login-code.button')}
                    onClick={navigateToReset}
                />
            }
            top={
                <AuthTopLink
                    text={t('login-code.top')}
                    onClick={sendResetCodeAgain}
                />
            }
            down={
                <AuthDownLink
                    firstLink={t('login-code.down')}
                    secondLink={t('center')}
                    firstOnClick={navigateToLogin}
                />
            }
        />
    )
}

export default LoginPasswordCodePage