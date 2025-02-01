import React from 'react'

export const passwordValidation = (
    password: string,
    setError: React.Dispatch<React.SetStateAction<string | null>>,
    t: (key: string) => string,
    setLoading: React.Dispatch<React.SetStateAction<boolean>>
): boolean => {
    if (!/[a-z]/.test(password)) {
        setError(t('signup.error-weak-password-lowercase'))
        setLoading(false)
        return false
    } else if (!/[A-Z]/.test(password)) {
        setError(t('signup.error-weak-password-uppercase'))
        setLoading(false)
        return false
    } else if (!/[0-9]/.test(password)) {
        setError(t('signup.error-weak-password-number'))
        setLoading(false)
        return false
    } else if (!/[!@#$%^&*?]/.test(password)) {
        setError(t('signup.error-weak-password-special'))
        setLoading(false)
        return false
    } else return true
}
