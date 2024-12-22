import React from 'react'

export const codeValidation = (
    code: string,
    setError: React.Dispatch<React.SetStateAction<string | null>>,
    text: string,
    setLoading: React.Dispatch<React.SetStateAction<boolean>>
): void => {
    if (code.length !== 6 || isNaN(Number(code))) {
        setError(text)
        setLoading(false)
        return
    }
}
