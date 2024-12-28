import React from 'react'
import { checkPasswordsMatch } from '../../../utils/checkPasswordsMatch.ts'
import { passwordValidation } from '../../../utils/passwordValidation.ts'
import { useRedirectEvery } from '../../../hooks/useRedirect.ts'
import { useAuthenticated } from '../../../store/slices/auth.ts'
import { useTranslation } from 'react-i18next'
import { useNavigate } from 'react-router-dom'
import { useReset } from '../../../store/slices/reset.ts'
import { useForm } from '../../../hooks/useForm.ts'
import { handle } from '../../../utils/handle.ts'
import AuthPanel from '../../../components/Auth/AuthPanel'
import AuthTitle from '../../../components/Auth/AuthTitle'
import AuthInput from '../../../components/Auth/AuthInput'
import AuthButton from '../../../components/Auth/AuthButton'
import AuthTopLink from '../../../components/Auth/AuthTopLink'
import AuthDownLink from '../../../components/Auth/AuthDownLink'
import AuthError from '../../../components/Auth/AuthError'
import { userService } from '../../../services/userService.ts'

interface FormData {
    password: string
    rePassword: string
}

const ResetPasswordPage = () => {
    const { t } = useTranslation()
    const navigate = useNavigate()
    const isAuthenticated = useAuthenticated()
    const isResetPassword = useReset()
    const { formData, error, setError, handleChange, resetForm, loading, setLoading } = useForm<FormData>({
        initialValues: {
            password: '',
            rePassword: ''
        }
    })

    useRedirectEvery([!isAuthenticated && !isResetPassword], '/reset-password/email-check')

    const handleResetPassword = async (e: React.FormEvent) => {
        handle(e, setLoading, setError)

        checkPasswordsMatch(formData.password, formData.rePassword, setError, t, setLoading)

        passwordValidation(formData.password, setError, t, setLoading)

        const response = await userService.changePassword(formData.password)
        if (response.status === 200) navigate('/')
        else {
            setError(t('login-reset.error-default'))
            resetForm()
            setLoading(false)
        }
    }

    return (
        <AuthPanel
            onSubmit={handleResetPassword}
            header={<AuthTitle text={t('login-reset.title')} />}
            inputOrText={
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
            firstInput={
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

export default ResetPasswordPage
