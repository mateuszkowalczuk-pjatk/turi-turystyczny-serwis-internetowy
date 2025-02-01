import React from 'react'
import { checkPasswordsMatch } from '../../../utils/checkPasswordsMatch.ts'
import { passwordValidation } from '../../../utils/passwordValidation.ts'
import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { useForm } from '../../../hooks/shared/useForm.ts'
import { handle } from '../../../utils/handle.ts'
import AuthPanel from '../../../components/Auth/AuthPanel'
import AuthTitle from '../../../components/Auth/AuthTitle'
import AuthInput from '../../../components/Auth/AuthInput'
import Error from '../../../components/Shared/Error'
import AuthButton from '../../../components/Auth/AuthButton'
import AuthTopLink from '../../../components/Auth/AuthTopLink'
import AuthDownLink from '../../../components/Auth/AuthDownLink'
import { userService } from '../../../services/userService.ts'
import { authService } from '../../../services/authService.ts'
import { activation } from '../../../store/slices/activate.ts'
import { logout } from '../../../store/slices/auth.ts'

interface FormData {
    login: string
    email: string
    password: string
    rePassword: string
}

const RegisterPage = () => {
    const { t, dispatch, navigate } = useHooks()
    const { formData, error, setError, handleChange, resetForm, loading, setLoading } = useForm<FormData>({
        initialValues: {
            login: '',
            email: '',
            password: '',
            rePassword: ''
        }
    })

    const handleRegister = async (e: React.FormEvent) => {
        handle(e, setLoading, setError)

        if (!checkPasswordsMatch(formData.password, formData.rePassword, setError, t, setLoading)) return

        if (!passwordValidation(formData.password, setError, t, setLoading)) return

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
            navigate('/activate')
        } else defaultAction()
    }

    const defaultAction = () => {
        setError(t('signup.error-default'))
        resetForm()
        setLoading(false)
    }

    return (
        <AuthPanel
            onSubmit={handleRegister}
            header={<AuthTitle text={t('signup.title')} />}
            inputOrText={
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
            }
            firstInput={
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
            }
            secondInput={
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
            }
            thirdInput={
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
            }
            error={error && <Error error={error} />}
            button={
                <AuthButton
                    text={t('signup.button')}
                    type={'submit'}
                    disabled={loading}
                />
            }
            top={<AuthTopLink />}
            down={
                <AuthDownLink
                    firstLink={t('signup.signin')}
                    secondLink={'right'}
                    firstOnClick={() => navigate('/login')}
                />
            }
            isRegister
        />
    )
}

export default RegisterPage
