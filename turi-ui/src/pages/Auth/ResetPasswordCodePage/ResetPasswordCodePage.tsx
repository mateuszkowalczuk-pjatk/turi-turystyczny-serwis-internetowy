import { useTranslation } from 'react-i18next'
import { useNavigate } from 'react-router-dom'
import AuthPanel from '../../../components/Auth/AuthPanel'
import AuthTitle from '../../../components/Auth/AuthTitle'
import AuthDescription from '../../../components/Auth/AuthDescription'
import AuthInput from '../../../components/Auth/AuthInput'
import AuthButton from '../../../components/Auth/AuthButton'
import AuthError from '../../../components/Auth/AuthError'
import AuthTopLink from '../../../components/Auth/AuthTopLink'
import AuthDownLink from '../../../components/Auth/AuthDownLink'
import React, { useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { RootState } from '../../../store/store.ts'
import { userService } from '../../../services/userService.ts'
import { notResetPassword } from '../../../store/slices/reset.ts'
import { login } from '../../../store/slices/auth.ts'
import { authService } from '../../../services/authService.ts'
import { useForm } from '../../../hooks/useForm.ts'
import { useRedirectEvery } from '../../../hooks/useRedirect.ts'
import { handle } from '../../../utils/handle.ts'
import { codeValidation } from '../../../utils/codeValidation.ts'

interface FormData {
    code: string
}

const ResetPasswordCodePage = () => {
    const { t } = useTranslation()
    const navigate = useNavigate()
    const isResetPassword = useSelector((state: RootState) => state.reset.isResetPassword)
    const dispatch = useDispatch()
    const [loading, setLoading] = useState(false)
    const { formData, error, setError, handleChange, resetForm } = useForm<FormData>({
        initialValues: {
            code: ''
        }
    })

    useRedirectEvery([!isResetPassword], '/reset-password/email-check')

    const handleResetPassword = async (e: React.FormEvent) => {
        handle(e, setLoading, setError)

        codeValidation(formData.code, setError, t('signup.incorrect-activate-code'), setLoading)

        const response = await userService.resetPassword(formData.code)
        if (response.status === 200) {
            dispatch(notResetPassword())
            const refresh = await authService.refresh()
            if (refresh.status === 200) {
                dispatch(login())
                navigate('/reset-password')
            }
        } else if (response.status === 400) {
            setError(t('login-code.error-reset-code-expired'))
            setLoading(false)
        } else if (response.status === 404) {
            setError(t('login-code.error-not-found-by-token'))
            setLoading(false)
        } else {
            setError(t('login-code.error-default'))
            resetForm()
            setLoading(false)
        }
    }

    return (
        <AuthPanel
            onSubmit={handleResetPassword}
            header={<AuthTitle text={t('login-code.title')} />}
            inputOrText={<AuthDescription text={t('login-code.description')} />}
            firstInput={
                <AuthInput
                    type={'number'}
                    name={'code'}
                    placeholder={t('login-code.code')}
                    value={formData.code}
                    onChange={handleChange}
                    required={true}
                    disabled={loading}
                />
            }
            error={error && <AuthError error={error} />}
            button={
                <AuthButton
                    text={t('login-code.button')}
                    disabled={loading}
                />
            }
            top={<AuthTopLink />}
            down={
                <AuthDownLink
                    firstLink={t('login-code.down')}
                    secondLink={t('center')}
                    firstOnClick={() => navigate('/login')}
                />
            }
        />
    )
}

export default ResetPasswordCodePage