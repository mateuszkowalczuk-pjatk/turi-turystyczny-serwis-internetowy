import React from 'react'

export const checkPasswordsMatch = (
    password: string,
    rePassword: string,
    setError: React.Dispatch<React.SetStateAction<string | null>>,
    t: (key: string) => string,
    setLoading: React.Dispatch<React.SetStateAction<boolean>>
): boolean => {
    if (password !== rePassword) {
        setError(t('signup.error-mismatch-password'))
        setLoading(false)
        return false
    } else return true
}
