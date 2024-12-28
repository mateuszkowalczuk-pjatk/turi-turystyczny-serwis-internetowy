import { useNavigate } from 'react-router-dom'
import { useEffect } from 'react'

export const useId = (id: number | null, path: string, modify: boolean) => {
    const navigate = useNavigate()

    useEffect(() => {
        if (!id && modify) navigate(path)
    }, [id, navigate, path])
}
