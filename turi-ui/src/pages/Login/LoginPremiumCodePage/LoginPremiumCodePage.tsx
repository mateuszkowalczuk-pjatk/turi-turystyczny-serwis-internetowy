import React, { useEffect, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useNavigate } from 'react-router-dom'
import AuthPanel from '../../../components/Auth/AuthPanel'
import AuthTitle from '../../../components/Auth/AuthTitle'
import AuthDescription from '../../../components/Auth/AuthDescription'
import AuthInput from '../../../components/Auth/AuthInput'
import AuthError from '../../../components/Auth/AuthError'
import AuthButton from '../../../components/Auth/AuthButton'
import AuthTopLink from '../../../components/Auth/AuthTopLink'
import AuthDownLink from '../../../components/Auth/AuthDownLink'
import { useDispatch, useSelector } from 'react-redux'
import { RootState } from '../../../store/store.ts'
import { authService } from '../../../services/authService.ts'
import { login } from '../../../store/slices/auth.ts'
import { notLoginPremium } from '../../../store/slices/premiumLogin.ts'
import { premiumAccount } from '../../../store/slices/premium.ts'

interface FormData {
    code: string
}

const LoginPremiumCodePage = () => {
    const { t } = useTranslation()
    const navigate = useNavigate()
    const [formData, setFormData] = useState<FormData>({
        code: ''
    })
    const isPremiumLogin = useSelector((state: RootState) => state.premiumLogin.isPremiumLogin)
    const dispatch = useDispatch()
    const [error, setError] = useState<string | null>(null)
    const [loading, setLoading] = useState(false)

    useEffect(() => {
        if (!isPremiumLogin) {
            navigate('/login')
        }
    }, [isPremiumLogin, navigate])

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setError(null)
        const { name, value } = e.target
        setFormData({ ...formData, [name]: value })
    }

    const handlePremiumLogin = async (e: React.FormEvent) => {
        e.preventDefault()
        setLoading(true)
        setError(null)

        if (formData.code.length !== 6 || isNaN(Number(formData.code))) {
            setError(t('login-premium.incorrect-activate-code'))
            setLoading(false)
            return
        }

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
            setFormData({
                code: ''
            })
            setLoading(false)
        }
    }

    return (
        <AuthPanel
            onSubmit={handlePremiumLogin}
            header={<AuthTitle text={t('login-premium.title')} />}
            option={<AuthDescription text={t('login-premium.description')} />}
            input={
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

export default LoginPremiumCodePage
