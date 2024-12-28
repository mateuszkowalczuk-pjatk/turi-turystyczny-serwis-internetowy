import { useNavigate } from 'react-router-dom'
import { useEffect } from 'react'

export const useRedirectEvery = (conditions: boolean[], path: string) => {
    const navigate = useNavigate()

    useEffect(() => {
        if (conditions.every(Boolean)) navigate(path)
    }, [conditions, path, navigate])
}

export const useRedirectSome = (conditions: boolean[], path: string) => {
    const navigate = useNavigate()

    useEffect(() => {
        if (conditions.some(Boolean)) navigate(path)
    }, [conditions, path, navigate])
}
