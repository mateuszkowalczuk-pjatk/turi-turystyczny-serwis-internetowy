export const handleFormError = (
    setError: (value: ((prevState: string | null) => string | null) | string | null) => void,
    setLoading: (value: ((prevState: boolean) => boolean) | boolean) => void,
    text: string
): void => {
    setError(text)
    setLoading(false)
    return
}
