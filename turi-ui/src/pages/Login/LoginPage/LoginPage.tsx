import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useTranslation } from 'react-i18next'
import AuthPanel from '../../../components/Auth/AuthPanel'
import AuthTitle from '../../../components/Auth/AuthTitle'
import AuthInput from '../../../components/Auth/AuthInput'
import AuthError from '../../../components/Auth/AuthError'
import AuthButton from '../../../components/Auth/AuthButton'
import AuthTopLink from '../../../components/Auth/AuthTopLink'
import AuthDownLink from '../../../components/Auth/AuthDownLink'
import { useDispatch, useSelector } from 'react-redux'
import { authService } from '../../../services/authService.ts'
import { login } from '../../../store/slices/auth.ts'
import { activation } from '../../../store/slices/activate.ts'
import { loginPremium } from '../../../store/slices/premiumLogin.ts'
import { RootState } from '../../../store/store.ts'

interface FormData {
    login: string
    password: string
}

const LoginPage = () => {
    const { t } = useTranslation()
    const navigate = useNavigate()
    const dispatch = useDispatch()
    const [formData, setFormData] = useState<FormData>({
        login: '',
        password: ''
    })
    const isPersonalization = useSelector((state: RootState) => state.personal.isPersonalization)
    const isAuthenticated = useSelector((state: RootState) => state.auth.isAuthenticated)
    const [error, setError] = useState<string | null>(null)
    const [loading, setLoading] = useState(false)

    useEffect(() => {
        if (isAuthenticated && !isPersonalization) {
            navigate('/')
        }
    }, [isAuthenticated, isPersonalization, navigate])

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setError(null)
        const { name, value } = e.target
        setFormData({ ...formData, [name]: value })
    }

    const handleLogin = async (e: React.FormEvent) => {
        e.preventDefault()
        setLoading(true)

        setError(null)

        const response = await authService.login({
            login: formData.login,
            password: formData.password
        })
        if (response.status === 200) {
            dispatch(login())
            if (isPersonalization) navigate('/signup/personal')
            else navigate('/')
        } else if (response.status === 202) {
            dispatch(activation())
            navigate('/signup/verify')
        } else if (response.status === 201) {
            dispatch(loginPremium())
            navigate('/login/premium-code')
        } else if (response.status === 401) {
            setError(t('login.error-credentials'))
            setLoading(false)
        } else {
            setError(t('login.error-default'))
            setFormData({
                login: '',
                password: ''
            })
            setLoading(false)
        }
    }

    const loginByGoogleAccount = () => {
        return
    }

    const navigateToCheck = () => {
        navigate('/login/check')
    }

    const navigateToSignUp = () => {
        navigate('/signup')
    }

    return (
        <AuthPanel
            onSubmit={handleLogin}
            header={<AuthTitle text={t('login.title')} />}
            option={
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
            input={
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
