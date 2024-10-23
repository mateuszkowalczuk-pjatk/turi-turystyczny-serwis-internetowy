import { useTranslation } from 'react-i18next'
import { useNavigate } from 'react-router-dom'
import AuthPanel from '../../../components/Auth/AuthPanel'
import AuthTitle from '../../../components/Auth/AuthTitle'
import AuthDescription from '../../../components/Auth/AuthDescription'
import AuthInput from '../../../components/Auth/AuthInput'
import AuthButton from '../../../components/Auth/AuthButton'
import AuthTopLink from '../../../components/Auth/AuthTopLink'
import AuthDownLink from '../../../components/Auth/AuthDownLink'

const LoginPasswordCheckPage = () => {
    const { t } = useTranslation()
    const navigate = useNavigate()

    const navigateToCode = () => {
        navigate('/login/code')
    }

    const navigateToLogin = () => {
        navigate('/login')
    }

    return (
        <AuthPanel
            header={<AuthTitle text={t('login-check.title')} />}
            option={<AuthDescription text={t('login-check.description')} />}
            input={<AuthInput text={t('login-check.email')} />}
            button={
                <AuthButton
                    text={t('login-check.button')}
                    onClick={navigateToCode}
                />
            }
            top={<AuthTopLink />}
            down={
                <AuthDownLink
                    firstLink={t('login-check.down')}
                    secondLink={t('center')}
                    firstOnClick={navigateToLogin}
                />
            }
        />
    )
}

export default LoginPasswordCheckPage
