import { API_BASE_URL } from '../config/api'
import { API } from './constants.ts'

export const authService = {
    register: async (username: string, email: string, password: string) => {
        const response = await fetch(`${API_BASE_URL}${API.AUTH.REGISTER}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username, email, password }),
            credentials: 'include'
        })

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`)
        }
    }
}
