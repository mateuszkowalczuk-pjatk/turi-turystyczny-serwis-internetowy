import { API_BASE_URL } from '../config/api'
import { API } from './constants.ts'

export const userService = {
    checkIsUsernameExists: async (username: string) => {
        const response = await fetch(
            `${API_BASE_URL}${API.USER.IS_USERNAME_EXISTS}?username=${encodeURIComponent(username)}`,
            {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
                credentials: 'include'
            }
        )

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`)
        }

        const isExists: boolean = await response.json()
        return { exists: isExists }
    },

    checkIsEmailExists: async (email: string) => {
        const response = await fetch(`${API_BASE_URL}${API.USER.IS_EMAIL_EXISTS}?email=${encodeURIComponent(email)}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`)
        }

        const isExists: boolean = await response.json()
        return { exists: isExists }
    }
}
