export const passwordValidation = (password: string, t: (key: string) => string): string | null => {
    if (!/[a-z]/.test(password)) {
        return t('signup.error-weak-password-lowercase')
    }

    if (!/[A-Z]/.test(password)) {
        return t('signup.error-weak-password-uppercase')
    }

    if (!/[0-9]/.test(password)) {
        return t('signup.error-weak-password-number')
    }

    if (!/[!@#$%^&*?]/.test(password)) {
        return t('signup.error-weak-password-special')
    }

    return null
}
