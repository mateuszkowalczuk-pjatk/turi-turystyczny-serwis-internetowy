import { useTranslation } from 'react-i18next'
import AuthPanel from '../../../components/Auth/AuthPanel'
import AuthTitle from '../../../components/Auth/AuthTitle'
import AuthInput from '../../../components/Auth/AuthInput'
import AuthButton from '../../../components/Auth/AuthButton'
import AuthTopLink from '../../../components/Auth/AuthTopLink'
import AuthDownLink from '../../../components/Auth/AuthDownLink'

const LoginPage = () => {
    const { t } = useTranslation();

    return (
        <AuthPanel
            header={
                <AuthTitle
                    text={t('login.title')}
                />
            }
            option={
                <AuthInput
                    text={t('login.login')}
                />
            }
            input={
                <AuthInput
                    text={t('login.password')}
                />
            }
            button={
                <AuthButton
                    text={t('login.button')}
                />
            }
            top={
                <AuthTopLink
                    text={t('login.google')}
                />
            }
            down={
                <AuthDownLink
                    firstLink={t('login.reset-password')}
                    secondLink={t('login.new-account')}
                />
            }
        />
    )
}

export default LoginPage;