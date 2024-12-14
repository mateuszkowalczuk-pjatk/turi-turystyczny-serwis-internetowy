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
import { accountService } from '../../../services/accountService.ts'
import { useDispatch, useSelector } from 'react-redux'
import { RootState } from '../../../store/store.ts'
import { notActivation } from '../../../store/slices/activate.ts'
import { personalization } from '../../../store/slices/personal.ts'

interface FormData {
    code: string
}

const SignUpVerifyPage = () => {
    const { t } = useTranslation()
    const navigate = useNavigate()
    const [formData, setFormData] = useState<FormData>({
        code: ''
    })
    const isActivation = useSelector((state: RootState) => state.activate.isActivation)
    const dispatch = useDispatch()
    const [error, setError] = useState<string | null>(null)
    const [loading, setLoading] = useState(false)

    useEffect(() => {
        if (!isActivation) {
            navigate('/login')
        }
    }, [isActivation, navigate])

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setError(null)
        const { name, value } = e.target
        setFormData({ ...formData, [name]: value })
    }

    const handleActivate = async (e: React.FormEvent) => {
        e.preventDefault()
        setLoading(true)
        setError(null)

        if (formData.code.length !== 6 || isNaN(Number(formData.code))) {
            setError(t('signup.incorrect-activate-code'))
            setLoading(false)
            return
        }

        const response = await accountService.activate(Number(formData.code))
        if (response.status === 200) {
            dispatch(notActivation())
            dispatch(personalization())
            navigate('/login')
        } else if (response.status === 400) {
            setError(t('signup-verify.error-code-expired'))
            setLoading(false)
        } else if (response.status === 404) {
            setError(t('signup-verify.error-invalid-code'))
            setLoading(false)
        } else {
            setError(t('signup-verify.error-default'))
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
            onSubmit={handleActivate}
            header={<AuthTitle text={t('signup-verify.title')} />}
            option={<AuthDescription text={t('signup-verify.description')} />}
            input={
                <AuthInput
                    type={'number'}
                    name={'code'}
                    placeholder={t('signup-verify.code')}
                    value={formData.code}
                    onChange={handleChange}
                    required={true}
                    disabled={loading}
                />
            }
            error={error && <AuthError error={error} />}
            button={
                <AuthButton
                    text={t('signup-verify.button')}
                    type={'submit'}
                    disabled={loading}
                />
            }
            top={<AuthTopLink />}
            down={
                <AuthDownLink
                    firstLink={t('signup-verify.down')}
                    secondLink={'center'}
                    firstOnClick={navigateToLogin}
                />
            }
        />
    )
}

export default SignUpVerifyPage
