import React from 'react'
import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { useForm } from '../../../hooks/shared/useForm.ts'
import { handle } from '../../../utils/handle.ts'
import AuthPanel from '../../../components/Auth/AuthPanel'
import AuthTitle from '../../../components/Auth/AuthTitle'
import AuthDescription from '../../../components/Auth/AuthDescription'
import AuthInput from '../../../components/Auth/AuthInput'
import AuthButton from '../../../components/Auth/AuthButton'
import AuthTopLink from '../../../components/Auth/AuthTopLink'
import AuthDownLink from '../../../components/Auth/AuthDownLink'
import AuthError from '../../../components/Auth/AuthError'
import { resetPassword } from '../../../store/slices/reset.ts'
import { userService } from '../../../services/userService.ts'

interface FormData {
    email: string
}

const ResetPasswordEmailCheckPage = () => {
    const { t, dispatch, navigate } = useHooks()
    const { formData, error, setError, handleChange, resetForm, loading, setLoading } = useForm<FormData>({
        initialValues: {
            email: ''
        }
    })

    const handleResetPassword = async (e: React.FormEvent) => {
        handle(e, setLoading, setError)

        const response = await userService.sendResetPasswordCode(formData.email)
        if (response.status === 200) {
            dispatch(resetPassword())
            navigate('/reset-password/code')
        } else if (response.status === 400) {
            setError(t('login-check.error-code-recently-send'))
            setLoading(false)
        } else if (response.status === 404) {
            setError(t('login-check.error-not-found-by-email'))
            setLoading(false)
        } else {
            setError(t('login-check.error-default'))
            resetForm()
            setLoading(false)
        }
    }

    return (
        <AuthPanel
            onSubmit={handleResetPassword}
            header={<AuthTitle text={t('login-check.title')} />}
            inputOrText={<AuthDescription text={t('login-check.description')} />}
            firstInput={
                <AuthInput
                    type={'email'}
                    name={'email'}
                    placeholder={t('login-check.email')}
                    value={formData.email}
                    onChange={handleChange}
                    minLength={5}
                    maxLength={50}
                    required={true}
                    disabled={loading}
                />
            }
            error={error && <AuthError error={error} />}
            button={
                <AuthButton
                    text={t('login-check.button')}
                    disabled={loading}
                />
            }
            top={<AuthTopLink />}
            down={
                <AuthDownLink
                    firstLink={t('login-check.down')}
                    secondLink={t('center')}
                    firstOnClick={() => navigate('/login')}
                />
            }
        />
    )
}

export default ResetPasswordEmailCheckPage
