import React from 'react'
import { useRedirectEvery } from '../../../hooks/useRedirect.ts'
import { codeValidation } from '../../../utils/codeValidation.ts'
import { useHooks } from '../../../hooks/useHooks.ts'
import { useForm } from '../../../hooks/useForm.ts'
import { handle } from '../../../utils/handle.ts'
import AuthPanel from '../../../components/Auth/AuthPanel'
import AuthTitle from '../../../components/Auth/AuthTitle'
import AuthDescription from '../../../components/Auth/AuthDescription'
import AuthInput from '../../../components/Auth/AuthInput'
import AuthError from '../../../components/Auth/AuthError'
import AuthButton from '../../../components/Auth/AuthButton'
import AuthTopLink from '../../../components/Auth/AuthTopLink'
import AuthDownLink from '../../../components/Auth/AuthDownLink'
import { notLoginPremium, usePremiumLogin } from '../../../store/slices/premiumLogin.ts'
import { premiumAccount } from '../../../store/slices/premium.ts'
import { authService } from '../../../services/authService.ts'
import { login } from '../../../store/slices/auth.ts'

interface FormData {
    code: string
}

const LoginCodePage = () => {
    const { t, dispatch, navigate } = useHooks()
    const isPremiumLogin = usePremiumLogin()
    const { formData, error, setError, handleChange, resetForm, loading, setLoading } = useForm<FormData>({
        initialValues: {
            code: ''
        }
    })

    useRedirectEvery([!isPremiumLogin], '/login')

    const handlePremiumLogin = async (e: React.FormEvent) => {
        handle(e, setLoading, setError)

        codeValidation(formData.code, setError, t('login-premium.incorrect-activate-code'), setLoading)

        const response = await authService.loginPremium(formData.code)
        if (response.status === 200) {
            const refresh = await authService.refresh()
            if (refresh.status === 200) {
                dispatch(login())
                dispatch(premiumAccount())
                dispatch(notLoginPremium())
                navigate('/')
            }
        } else if (response.status === 400) {
            setError(t('login-premium.error-login-code-expired'))
            setLoading(false)
        } else if (response.status === 404) {
            setError(t('login-premium.error-not-found-by-token'))
            setLoading(false)
        } else {
            setError(t('login-premium.error-default'))
            resetForm()
            setLoading(false)
        }
    }

    return (
        <AuthPanel
            onSubmit={handlePremiumLogin}
            header={<AuthTitle text={t('login-premium.title')} />}
            inputOrText={<AuthDescription text={t('login-premium.description')} />}
            firstInput={
                <AuthInput
                    type={'number'}
                    name={'code'}
                    placeholder={t('login-premium.code')}
                    value={formData.code}
                    onChange={handleChange}
                    required={true}
                    disabled={loading}
                />
            }
            error={error && <AuthError error={error} />}
            button={
                <AuthButton
                    text={t('login-premium.button')}
                    disabled={loading}
                />
            }
            top={<AuthTopLink />}
            down={
                <AuthDownLink
                    firstLink={t('login-premium.down')}
                    secondLink={t('center')}
                    firstOnClick={() => navigate('/login')}
                />
            }
        />
    )
}

export default LoginCodePage
