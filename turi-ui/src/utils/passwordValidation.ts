import React from 'react'

export const passwordValidation = (
    password: string,
    setError: React.Dispatch<React.SetStateAction<string | null>>,
    t: (key: string) => string,
    setLoading: React.Dispatch<React.SetStateAction<boolean>>
): void => {
    if (!/[a-z]/.test(password)) {
        setError(t('signup.error-weak-password-lowercase'))
        setLoading(false)
        return
    }

    if (!/[A-Z]/.test(password)) {
        setError(t('signup.error-weak-password-uppercase'))
        setLoading(false)
        return
    }

    if (!/[0-9]/.test(password)) {
        setError(t('signup.error-weak-password-number'))
        setLoading(false)
        return
    }

    if (!/[!@#$%^&*?]/.test(password)) {
        setError(t('signup.error-weak-password-special'))
        setLoading(false)
        return
    }
}
