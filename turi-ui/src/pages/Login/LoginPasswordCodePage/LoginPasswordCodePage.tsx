import {useTranslation} from 'react-i18next'
import {useNavigate} from 'react-router-dom'
import AuthPanel from '../../../components/Auth/AuthPanel'
import AuthTitle from '../../../components/Auth/AuthTitle'
import AuthDescription from '../../../components/Auth/AuthDescription'
import AuthInput from '../../../components/Auth/AuthInput'
import AuthButton from '../../../components/Auth/AuthButton'
import AuthTopLink from '../../../components/Auth/AuthTopLink'
import AuthDownLink from '../../../components/Auth/AuthDownLink'
import React, {useEffect, useState} from 'react'
import {useDispatch, useSelector} from 'react-redux'
import {RootState} from '../../../store/store.ts'
import {userService} from '../../../services/userService.ts'
import AuthError from '../../../components/Auth/AuthError'
import {notResetPassword} from '../../../store/slices/reset.ts'
import {login} from "../../../store/slices/auth.ts";
import {authService} from "../../../services/authService.ts";

interface FormData {
    code: string
}

const LoginPasswordCodePage = () => {
    const { t } = useTranslation()
    const navigate = useNavigate()
    const [formData, setFormData] = useState<FormData>({
        code: ''
    })
    const isResetPassword = useSelector((state: RootState) => state.reset.isResetPassword)
    const dispatch = useDispatch()
    const [error, setError] = useState<string | null>(null)
    const [loading, setLoading] = useState(false)

    useEffect(() => {
        if (!isResetPassword) {
            navigate('/login/check')
        }
    }, [isResetPassword, navigate])

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setError(null)
        const { name, value } = e.target
        setFormData({ ...formData, [name]: value })
    }

    const handleResetPassword = async (e: React.FormEvent) => {
        e.preventDefault()
        setLoading(true)
        setError(null)

        if (formData.code.length !== 6 || isNaN(Number(formData.code))) {
            setError(t('signup.incorrect-activate-code'))
            setLoading(false)
            return
        }

        const response = await userService.resetPassword(formData.code)
        if (response.status === 200) {
            dispatch(notResetPassword())
            const refresh = await authService.refresh()
            if (refresh.status === 200) {
                dispatch(login())
                navigate('/login/reset')
            }
        } else if (response.status === 400) {
            setError(t('login-code.error-reset-code-expired'))
            setLoading(false)
        } else if (response.status === 404) {
            setError(t('login-code.error-not-found-by-token'))
            setLoading(false)
        } else {
            setError(t('login-code.error-default'))
            setFormData({
                code: ''
            })
            setLoading(false)
        }
    }

    const navigateToLogin = () => {
        navigate('/login')
    }

    return (
        <AuthPanel
            onSubmit={handleResetPassword}
            header={<AuthTitle text={t('login-code.title')} />}
            option={<AuthDescription text={t('login-code.description')} />}
            input={
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
            button={<AuthButton
                text={t('login-code.button')}
                disabled={loading}
            />}
            top={<AuthTopLink />}
            down={
                <AuthDownLink
                    firstLink={t('login-code.down')}
                    secondLink={t('center')}
                    firstOnClick={navigateToLogin}
                />
            }
        />
    )
}

export default LoginPasswordCodePage
