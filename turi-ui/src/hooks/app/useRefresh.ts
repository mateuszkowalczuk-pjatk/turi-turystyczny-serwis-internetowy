import { useEffect } from 'react'
import { authService } from '../../services/authService.ts'
import { logout } from '../../store/slices/auth.ts'

export const useRefresh = () => {
    useEffect(() => {
        const interval = setInterval(async () => {
            const response = await authService.refresh()
            if (response.status !== 200) {
                console.error('Failed to refresh token')
                logout()
            } else {
                console.error('Token refreshed')
            }
        }, 840000)
        return () => clearInterval(interval)
    }, [])
}
