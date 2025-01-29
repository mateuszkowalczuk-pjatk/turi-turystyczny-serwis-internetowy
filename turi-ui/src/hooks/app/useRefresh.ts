import { useStates } from '../shared/useStates.ts'
import { useEffect } from 'react'
import { authService } from '../../services/authService.ts'
import { logout } from '../../store/slices/auth.ts'

export const useRefresh = () => {
    const { isAuthenticated } = useStates()

    useEffect(() => {
        const checkAuthAndRefresh = async () => {
            if (isAuthenticated) {
                const response = await authService.refresh()
                if (response.status !== 200) logout()
            }
        }

        const interval = setInterval(
            () => {
                checkAuthAndRefresh().catch((error) => error)
            },
            isAuthenticated ? 840000 : 60000
        )

        return () => clearInterval(interval)
    }, [isAuthenticated])
}
