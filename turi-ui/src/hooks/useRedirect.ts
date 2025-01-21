import { useEffect } from 'react'
import { useStates } from './useStates.ts'
import { useHooks } from './useHooks.ts'

export const useRedirectEvery = (conditions: boolean[], path: string) => {
    const isLoading = useStates()
    const { navigate } = useHooks()

    useEffect(() => {
        if (!isLoading && conditions.every(Boolean)) navigate(path)
    }, [isLoading, conditions, path, navigate])
}

export const useRedirectSome = (conditions: boolean[], path: string) => {
    const isLoading = useStates()
    const { navigate } = useHooks()

    useEffect(() => {
        if (!isLoading && conditions.some(Boolean)) navigate(path)
    }, [isLoading, conditions, path, navigate])
}
