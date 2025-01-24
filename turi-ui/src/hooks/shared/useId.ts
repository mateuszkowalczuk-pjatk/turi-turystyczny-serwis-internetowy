import { useHooks } from './useHooks.ts'
import { useEffect } from 'react'

export const useId = (id: number | null, path: string, modify: boolean) => {
    const { navigate } = useHooks()

    useEffect(() => {
        if (!id && modify) navigate(path)
    }, [id, navigate, path])
}
