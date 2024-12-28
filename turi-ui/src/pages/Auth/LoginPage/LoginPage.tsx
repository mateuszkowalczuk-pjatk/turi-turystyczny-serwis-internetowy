import React from 'react'
import { useRedirectEvery } from '../../../hooks/useRedirect.ts'
import { useTranslation } from 'react-i18next'
import { useAppDispatch } from '../../../hooks/useAppDispatch.ts'
import { usePersonal } from '../../../store/slices/personal.ts'
import { useNavigate } from 'react-router-dom'
import { useForm } from '../../../hooks/useForm.ts'
import { handle } from '../../../utils/handle.ts'
import AuthPanel from '../../../components/Auth/AuthPanel'
import AuthTitle from '../../../components/Auth/AuthTitle'
import AuthInput from '../../../components/Auth/AuthInput'
import AuthError from '../../../components/Auth/AuthError'
import AuthButton from '../../../components/Auth/AuthButton'
import AuthTopLink from '../../../components/Auth/AuthTopLink'
import AuthDownLink from '../../../components/Auth/AuthDownLink'
import { login, useAuthenticated } from '../../../store/slices/auth.ts'
import { loginPremium } from '../../../store/slices/premiumLogin.ts'
import { activation } from '../../../store/slices/activate.ts'
import { authService } from '../../../services/authService.ts'

interface FormData {
    login: string
    password: string
}

const LoginPage = () => {
    const { t } = useTranslation()
    const dispatch = useAppDispatch()
    const navigate = useNavigate()
    const isAuthenticated = useAuthenticated()
    const isPersonalization = usePersonal()
    const { formData, error, setError, handleChange, resetForm, loading, setLoading } = useForm<FormData>({
        initialValues: {
            login: '',
            password: ''
        }
    })

    useRedirectEvery([isAuthenticated, !isPersonalization], '/')

    const handleLogin = async (e: React.FormEvent) => {
        handle(e, setLoading, setError)

        const response = await authService.login({
            login: formData.login,
            password: formData.password
        })
        if (response.status === 200) {
            dispatch(login())
            if (isPersonalization) navigate('/personal')
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
            error={error && <AuthError error={error} />}
            button={
                <AuthButton
                    text={t('login.button')}
                    type={'submit'}
                    disabled={loading}
                />
            }
            top={
                <AuthTopLink
                    text={t('login.google')}
                    onClick={() => console.log('Google')}
                />
            }
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
