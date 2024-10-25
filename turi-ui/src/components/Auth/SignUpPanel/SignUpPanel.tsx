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

interface FormData {
    login: string
    email: string
    password: string
    rePassword: string
}

const SignUpPanel = () => {
    const { t } = useTranslation()
    const navigate = useNavigate()
    const [formData, setFormData] = useState<FormData>({
        login: '',
        email: '',
        password: '',
        rePassword: ''
    })
    const [error, setError] = useState<string | null>(null)

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setError(null)
        const { name, value } = e.target
        setFormData({ ...formData, [name]: value })
    }

    const handleSignUp = async (e: React.FormEvent) => {
        e.preventDefault()

        setError(null)

        if (formData.password !== formData.rePassword) {
            setError(t('signup.error-mismatch-password'))
            return
        }

        const passwordError = passwordValidation(formData.password, t)
        if (passwordError) {
            setError(passwordError)
            return
        }

        const loginCheck = await userService.checkIsUsernameExists(formData.login)
        if (loginCheck.exists) {
            setError(t('signup.error-username-exists'))
            return
        }

        const emailCheck = await userService.checkIsEmailExists(formData.email)
        if (emailCheck.exists) {
            setError(t('signup.error-email-exists'))
            return
        }

        await authService.register(formData.login, formData.email, formData.password)
        navigate('/signup/verify')
    }

    const navigateToLogin = () => {
        navigate('/login')
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
                minLength={2}
                maxLength={50}
                required={true}
            />
            <AuthInput
                type={'email'}
                name={'email'}
                placeholder={t('signup.email')}
                value={formData.email}
                onChange={handleChange}
                minLength={2}
                maxLength={50}
                required={true}
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
            />
            {error && <AuthError error={error} />}
            <AuthButton
                text={t('signup.button')}
                type={'submit'}
            />
            <AuthTopLink />
            <AuthDownLink
                firstLink={t('signup.signin')}
                secondLink={'right'}
                firstOnClick={navigateToLogin}
            />
        </form>
    )
}

export default SignUpPanel
