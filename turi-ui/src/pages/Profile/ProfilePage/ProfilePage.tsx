import React, { useEffect, useState } from 'react'
import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { useStates } from '../../../hooks/shared/useStates.ts'
import { useRedirectEvery } from '../../../hooks/shared/useRedirect.ts'
import { passwordValidation } from '../../../utils/passwordValidation.ts'
import ProfileModule from '../../../components/Profile/ProfileModule'
import ProfileButton from '../../../components/Profile/ProfileButton'
import { userService } from '../../../services/userService.ts'
import styles from './ProfilePage.module.css'

interface FormData {
    login: string
    email: string
    password: string
    rePassword: string
}

const ProfilePage = () => {
    const { t, navigate } = useHooks()
    const { isAuthenticated } = useStates()
    const [login, setLogin] = useState<string>('')
    const [email, setEmail] = useState<string>('')
    const [formData, setFormData] = useState<FormData>({
        login: '',
        email: '',
        password: '',
        rePassword: ''
    })
    const [error, setError] = useState<string | null>(null)
    const [loading, setLoading] = useState(false)

    useRedirectEvery([!isAuthenticated], '/')

    useEffect(() => {
        const fetchUsernameAndEmail = async () => {
            const usernameResponse = await userService.getUsername()
            if (usernameResponse.status === 200) {
                const username = await usernameResponse.text()
                setLogin(username)
                setFormData((prevData) => ({ ...prevData, login: username }))
            } else {
                setError('')
            }
            const emailResponse = await userService.getEmail()
            if (emailResponse.status === 200) {
                const email = await emailResponse.text()
                setEmail(email)
                setFormData((prevData) => ({ ...prevData, email: email }))
            } else {
                setError('')
            }
        }

        fetchUsernameAndEmail().catch((error) => error)
    }, [isAuthenticated, navigate])

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setError(null)
        const { name, value } = e.target
        setFormData({ ...formData, [name]: value })
    }

    const handleSave = async () => {
        setLoading(true)
        setError(null)

        try {
            if (formData.login !== login && formData.login !== '') {
                const usernameResponse = await userService.changeUsername(formData.login)
                if (usernameResponse.status !== 200) {
                    setError(t('profile.error-login'))
                }
            }
            if (formData.email !== email && formData.email !== '') {
                const emailResponse = await userService.changeEmail(formData.email)
                if (emailResponse.status !== 200) {
                    setError(t('profile.error-email'))
                }
            }

            if (formData.password) {
                if (formData.password && formData.password !== formData.rePassword) {
                    setError(t('profile.error-password-mismatch'))
                    setLoading(false)
                    return
                }

                if (!passwordValidation(formData.password, setError, t, setLoading)) return

                const passwordResponse = await userService.changePassword(formData.password)
                if (passwordResponse.status !== 200) {
                    setError(t('profile.error-password'))
                }
            }
        } catch (error) {
            setError(t('profile.error-default'))
        } finally {
            setLoading(false)
        }
    }

    return (
        <div className={styles.page}>
            <ProfileModule
                text={t('profile.login')}
                type="text"
                name="login"
                placeholder={t('profile.login')}
                value={formData.login}
                onChange={handleChange}
                required={true}
                disabled={loading}
            />
            <ProfileModule
                text={t('profile.email')}
                type="email"
                name="email"
                placeholder={t('profile.email')}
                value={formData.email}
                onChange={handleChange}
                required={true}
                disabled={loading}
            />
            <ProfileModule
                text={t('profile.password')}
                type="password"
                name="password"
                placeholder={t('profile.password')}
                value={formData.password}
                onChange={handleChange}
                required={false}
                disabled={loading}
            />
            <ProfileModule
                text={t('profile.re-password')}
                type="password"
                name="rePassword"
                placeholder={t('profile.re-password')}
                value={formData.rePassword}
                onChange={handleChange}
                required={false}
                disabled={loading}
            />
            {error && <div className={styles.error}>{error}</div>}
            <ProfileButton handleSave={handleSave} />
        </div>
    )
}

export default ProfilePage
