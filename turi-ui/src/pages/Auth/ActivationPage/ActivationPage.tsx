import React, { useState } from 'react'
import { useRedirectEvery } from '../../../hooks/useRedirect.ts'
import { useTranslation } from 'react-i18next'
import { codeValidation } from '../../../utils/codeValidation.ts'
import { useAppDispatch } from '../../../hooks/useAppDispatch.ts'
import { useNavigate } from 'react-router-dom'
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
import { notActivation, useActivation } from '../../../store/slices/activate.ts'
import { accountService } from '../../../services/accountService.ts'
import { personalization } from '../../../store/slices/personal.ts'

interface FormData {
    code: string
}

const ActivationPage = () => {
    const { t } = useTranslation()
    const dispatch = useAppDispatch()
    const navigate = useNavigate()
    const isActivation = useActivation()
    const [loading, setLoading] = useState(false)
    const { formData, error, setError, handleChange, resetForm } = useForm<FormData>({
        initialValues: {
            code: ''
        }
    })

    useRedirectEvery([!isActivation], '/login')

    const handleActivate = async (e: React.FormEvent) => {
        handle(e, setLoading, setError)

        codeValidation(formData.code, setError, t('signup.incorrect-activate-code'), setLoading)

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
            resetForm()
            setLoading(false)
        }
    }

    return (
        <AuthPanel
            onSubmit={handleActivate}
            header={<AuthTitle text={t('signup-verify.title')} />}
            inputOrText={<AuthDescription text={t('signup-verify.description')} />}
            firstInput={
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
                    firstOnClick={() => navigate('/login')}
                />
            }
        />
    )
}

export default ActivationPage
