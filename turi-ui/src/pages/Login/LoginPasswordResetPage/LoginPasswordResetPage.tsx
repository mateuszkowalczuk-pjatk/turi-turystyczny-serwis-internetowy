import { useTranslation } from 'react-i18next'
import { useNavigate } from 'react-router-dom'
import AuthPanel from '../../../components/Auth/AuthPanel'
import AuthTitle from '../../../components/Auth/AuthTitle'
import AuthInput from '../../../components/Auth/AuthInput'
import AuthButton from '../../../components/Auth/AuthButton'
import AuthTopLink from '../../../components/Auth/AuthTopLink'
import AuthDownLink from '../../../components/Auth/AuthDownLink'
import React, { useEffect, useState } from 'react'
import { useSelector } from 'react-redux'
import { RootState } from '../../../store/store.ts'
import { passwordValidation } from '../../../utils/passwordValidation.ts'
import AuthError from '../../../components/Auth/AuthError'
import { userService } from '../../../services/userService.ts'

interface FormData {
    password: string
    rePassword: string
}

const LoginPasswordResetPage = () => {
    const { t } = useTranslation()
    const navigate = useNavigate()
    const [formData, setFormData] = useState<FormData>({
        password: '',
        rePassword: ''
    })
    const isAuthenticated = useSelector((state: RootState) => state.auth.isAuthenticated)
    const isResetPassword = useSelector((state: RootState) => state.reset.isResetPassword)
    const [error, setError] = useState<string | null>(null)
    const [loading, setLoading] = useState(false)

    useEffect(() => {
        if (!isAuthenticated && !isResetPassword) {
            navigate('/login/check')
        }
    }, [isAuthenticated, isResetPassword, navigate])

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setError(null)
        const { name, value } = e.target
        setFormData({ ...formData, [name]: value })
    }

    const handleResetPassword = async (e: React.FormEvent) => {
        e.preventDefault()
        setLoading(true)
        setError(null)

        if (formData.password !== formData.rePassword) {
            setError(t('signup.error-mismatch-password'))
            setLoading(false)
            return
        }

        const passwordError = passwordValidation(formData.password, t)
        if (passwordError) {
            setError(passwordError)
            setLoading(false)
            return
        }

        const response = await userService.changePassword(formData.password)
        if (response.status === 200) {
            navigate('/')
        } else {
            setError(t('login-reset.error-default'))
            setFormData({
                password: '',
                rePassword: ''
            })
            setLoading(false)
        }
    }

    return (
        <AuthPanel
            onSubmit={handleResetPassword}
            header={<AuthTitle text={t('login-reset.title')} />}
            option={
                <AuthInput
                    type={'password'}
                    name={'password'}
                    placeholder={t('login-reset.password')}
                    value={formData.password}
                    onChange={handleChange}
                    minLength={8}
                    maxLength={25}
                    required={true}
                    disabled={loading}
                />
            }
            input={
                <AuthInput
                    type={'password'}
                    name={'rePassword'}
                    placeholder={t('login-reset.re-password')}
                    value={formData.rePassword}
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
                    text={t('login-reset.button')}
                    disabled={loading}
                />
            }
            top={
                <AuthTopLink
                    text={t('login-reset.top')}
                    onClick={() => navigate('/')}
                />
            }
            down={
                <AuthDownLink
                    firstLink={t('login-reset.down')}
                    secondLink={t('center')}
                    firstOnClick={() => navigate('/login')}
                />
            }
        />
    )
}

export default LoginPasswordResetPage
