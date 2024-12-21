import { useTranslation } from 'react-i18next'
import { useNavigate } from 'react-router-dom'
import AuthPanel from '../../../components/Auth/AuthPanel'
import AuthTitle from '../../../components/Auth/AuthTitle'
import AuthDescription from '../../../components/Auth/AuthDescription'
import AuthInput from '../../../components/Auth/AuthInput'
import AuthButton from '../../../components/Auth/AuthButton'
import AuthTopLink from '../../../components/Auth/AuthTopLink'
import AuthDownLink from '../../../components/Auth/AuthDownLink'
import React, { useState } from 'react'
import { useDispatch } from 'react-redux'
import { userService } from '../../../services/userService.ts'
import { resetPassword } from '../../../store/slices/reset.ts'
import AuthError from '../../../components/Auth/AuthError'

interface FormData {
    email: string
}

const LoginPasswordCheckPage = () => {
    const { t } = useTranslation()
    const navigate = useNavigate()
    const [formData, setFormData] = useState<FormData>({
        email: ''
    })
    const dispatch = useDispatch()
    const [error, setError] = useState<string | null>(null)
    const [loading, setLoading] = useState(false)

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setError(null)
        const { name, value } = e.target
        setFormData({ ...formData, [name]: value })
    }

    const handleResetPassword = async (e: React.FormEvent) => {
        e.preventDefault()
        setLoading(true)
        setError(null)

        const response = await userService.sendResetPasswordCode(formData.email)
        if (response.status === 200) {
            dispatch(resetPassword())
            navigate('/login/code')
        } else if (response.status === 400) {
            setError(t('login-check.error-code-recently-send'))
            setLoading(false)
        } else if (response.status === 404) {
            setError(t('login-check.error-not-found-by-email'))
            setLoading(false)
        } else {
            setError(t('login-check.error-default'))
            setFormData({
                email: ''
            })
            setLoading(false)
        }
    }

    return (
        <AuthPanel
            onSubmit={handleResetPassword}
            header={<AuthTitle text={t('login-check.title')} />}
            option={<AuthDescription text={t('login-check.description')} />}
            input={
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

export default LoginPasswordCheckPage
