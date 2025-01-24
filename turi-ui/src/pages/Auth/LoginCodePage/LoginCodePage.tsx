import React from 'react'
import { handle } from '../../../utils/handle.ts'
import { useForm } from '../../../hooks/shared/useForm.ts'
import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { useStates } from '../../../hooks/shared/useStates.ts'
import { codeValidation } from '../../../utils/codeValidation.ts'
import { useRedirectEvery } from '../../../hooks/shared/useRedirect.ts'
import Error from '../../../components/Shared/Error'
import AuthPanel from '../../../components/Auth/AuthPanel'
import AuthTitle from '../../../components/Auth/AuthTitle'
import AuthInput from '../../../components/Auth/AuthInput'
import AuthButton from '../../../components/Auth/AuthButton'
import AuthTopLink from '../../../components/Auth/AuthTopLink'
import AuthDownLink from '../../../components/Auth/AuthDownLink'
import AuthDescription from '../../../components/Auth/AuthDescription'
import { CodeFormData } from '../../../types/forms/codeFormData.ts'
import { login } from '../../../store/slices/auth.ts'
import { authService } from '../../../services/authService.ts'
import { premiumAccount } from '../../../store/slices/premium.ts'
import { notLoginPremium } from '../../../store/slices/premiumLogin.ts'

const LoginCodePage = () => {
    const { t, dispatch, navigate } = useHooks()
    const { isPremiumLogin } = useStates()
    const { formData, error, setError, handleChange, resetForm, loading, setLoading } = useForm<CodeFormData>({
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
            error={error && <Error error={error} />}
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
