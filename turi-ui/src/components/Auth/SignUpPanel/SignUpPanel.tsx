import React, { useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useNavigate } from 'react-router-dom'
import AuthTitle from '../AuthTitle'
import AuthInput from '../AuthInput'
import AuthError from '../AuthError'
import AuthButton from '../AuthButton'
import AuthTopLink from '../AuthTopLink'
import AuthDownLink from '../AuthDownLink'
import styles from './SignUpPanel.module.css'
import { authService } from '../../../services/authService'
import { userService } from '../../../services/userService'
import { passwordValidation } from '../../../utils/passwordValidation.ts'
import { useDispatch } from 'react-redux'
import { activation } from '../../../store/slices/activate.ts'
import { logout } from '../../../store/slices/auth.ts'

interface FormData {
    login: string
    email: string
    password: string
    rePassword: string
}

const SignUpPanel = () => {
    const { t } = useTranslation()
    const navigate = useNavigate()
    const dispatch = useDispatch()
    const [formData, setFormData] = useState<FormData>({
        login: '',
        email: '',
        password: '',
        rePassword: ''
    })
    const [error, setError] = useState<string | null>(null)
    const [loading, setLoading] = useState(false)

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setError(null)
        const { name, value } = e.target
        setFormData({ ...formData, [name]: value })
    }

    const handleSignUp = async (e: React.FormEvent) => {
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

        const usernameResponse = await userService.checkIsUsernameExists(formData.login)
        if (usernameResponse.status === 200) {
            const exists: boolean = await usernameResponse.json()
            if (exists) {
                setError(t('signup.error-username-exists'))
                setLoading(false)
                return
            }
        } else defaultAction()

        const emailResponse = await userService.checkIsEmailExists(formData.email)
        if (emailResponse.status === 200) {
            const exists: boolean = await emailResponse.json()
            if (exists) {
                setError(t('signup.error-email-exists'))
                setLoading(false)
                return
            }
        } else defaultAction()

        const response = await authService.register({
            username: formData.login,
            email: formData.email,
            password: formData.password
        })
        if (response.status === 202) {
            await authService.logout()
            dispatch(activation())
            dispatch(logout())
            navigate('/signup/verify')
        } else defaultAction()
    }

    const defaultAction = () => {
        setError(t('signup.error-default'))
        setFormData({
            login: '',
            email: '',
            password: '',
            rePassword: ''
        })
        setLoading(false)
    }

    return (
        <form
            className={styles.panel}
            onSubmit={handleSignUp}
        >
            <AuthTitle text={t('signup.title')} />
            <AuthInput
                type={'text'}
                name={'login'}
                placeholder={t('signup.login')}
                value={formData.login}
                onChange={handleChange}
                minLength={3}
                maxLength={50}
                required={true}
                disabled={loading}
            />
            <AuthInput
                type={'email'}
                name={'email'}
                placeholder={t('signup.email')}
                value={formData.email}
                onChange={handleChange}
                minLength={5}
                maxLength={50}
                required={true}
                disabled={loading}
            />
            <AuthInput
                type={'password'}
                name={'password'}
                placeholder={t('signup.password')}
                value={formData.password}
                onChange={handleChange}
                minLength={8}
                maxLength={25}
                required={true}
                disabled={loading}
            />
            <AuthInput
                type={'password'}
                name={'rePassword'}
                placeholder={t('signup.re-password')}
                value={formData.rePassword}
                onChange={handleChange}
                minLength={8}
                maxLength={25}
                required={true}
                disabled={loading}
            />
            {error && <AuthError error={error} />}
            <AuthButton
                text={t('signup.button')}
                type={'submit'}
                disabled={loading}
            />
            <AuthTopLink />
            <AuthDownLink
                firstLink={t('signup.signin')}
                secondLink={'right'}
                firstOnClick={() => navigate('/login')}
            />
        </form>
    )
}

export default SignUpPanel
