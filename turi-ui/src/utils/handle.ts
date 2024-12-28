import React from 'react'

export const handle = (
    e: React.FormEvent,
    setLoading: React.Dispatch<React.SetStateAction<boolean>>,
    setError: React.Dispatch<React.SetStateAction<string | null>>
) => {
    e.preventDefault()
    setLoading(true)
    setError(null)
}
