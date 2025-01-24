import React from 'react'
import { handle } from '../../../utils/handle.ts'
import { useForm } from '../../../hooks/shared/useForm.ts'
import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { useStates } from '../../../hooks/shared/useStates.ts'
import { useRedirectEvery } from '../../../hooks/shared/useRedirect.ts'
import Error from '../../../components/Shared/Error'
import AuthPanel from '../../../components/Auth/AuthPanel'
import AuthTitle from '../../../components/Auth/AuthTitle'
import AuthInput from '../../../components/Auth/AuthInput'
import AuthButton from '../../../components/Auth/AuthButton'
import AuthTopLink from '../../../components/Auth/AuthTopLink'
import AuthDownLink from '../../../components/Auth/AuthDownLink'
import { login } from '../../../store/slices/auth.ts'
import { activation } from '../../../store/slices/activate.ts'
import { authService } from '../../../services/authService.ts'
import { loginPremium } from '../../../store/slices/premiumLogin.ts'
import { LoginFormData } from '../../../types/forms/loginFormData.ts'

const LoginPage = () => {
    const { t, dispatch, navigate } = useHooks()
    const { isAuthenticated, isPersonal } = useStates()
    const { formData, error, setError, handleChange, resetForm, loading, setLoading } = useForm<LoginFormData>({
        initialValues: {
            login: '',
            password: ''
        }
    })

    useRedirectEvery([isAuthenticated, !isPersonal], '/')

    const handleLogin = async (e: React.FormEvent) => {
        handle(e, setLoading, setError)

        const response = await authService.login({
            login: formData.login,
            password: formData.password
        })
        if (response.status === 200) {
            dispatch(login())
            if (isPersonal) navigate('/personal')
            else navigate('/')
        } else if (response.status === 202) {
            dispatch(activation())
            navigate('/activate')
        } else if (response.status === 201) {
            dispatch(loginPremium())
            navigate('/login/code')
        } else if (response.status === 401) {
            setError(t('login.error-credentials'))
            setLoading(false)
        } else {
            setError(t('login.error-default'))
            resetForm()
            setLoading(false)
        }
    }

    return (
        <AuthPanel
            onSubmit={handleLogin}
            header={<AuthTitle text={t('login.title')} />}
            inputOrText={
                <AuthInput
                    type={'text'}
                    name={'login'}
                    placeholder={t('login.login')}
                    value={formData.login}
                    onChange={handleChange}
                    minLength={3}
                    maxLength={50}
                    required={true}
                    disabled={loading}
                />
            }
            firstInput={
                <AuthInput
                    type={'password'}
                    name={'password'}
                    placeholder={t('login.password')}
                    value={formData.password}
                    onChange={handleChange}
                    minLength={8}
                    maxLength={25}
                    required={true}
                    disabled={loading}
                />
            }
            error={error && <Error error={error} />}
            button={
                <AuthButton
                    text={t('login.button')}
                    type={'submit'}
                    disabled={loading}
                />
            }
            top={<AuthTopLink />}
            down={
                <AuthDownLink
                    firstLink={t('login.reset')}
                    secondLink={t('login.signup')}
                    firstOnClick={() => navigate('/reset-password/email-check')}
                    secondOnClick={() => navigate('/register')}
                />
            }
        />
    )
}

export default LoginPage
