import { API_BASE_URL } from '../config/api'
import { API } from './constants.ts'

export const userService = {
    checkIsUsernameExists: async (username: string) => {
        return await fetch(`${API_BASE_URL}${API.USER.IS_USERNAME_EXISTS}?username=${username}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })
    },

    checkIsEmailExists: async (email: string) => {
        return await fetch(`${API_BASE_URL}${API.USER.IS_EMAIL_EXISTS}?email=${email}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })
    },

    sendResetPasswordCode: async (email: string) => {
        const response = await fetch(`${API_BASE_URL}${API.USER.SEND_RESET_PASSWORD_CODE}?email=${email}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`)
        }
    },

    resetPassword: async (code: string) => {
        const response = await fetch(`${API_BASE_URL}${API.USER.RESET_PASSWORD}?code=${code}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`)
        }
    },

    changeUsername: async (username: string) => {
        const response = await fetch(`${API_BASE_URL}${API.USER.CHANGE_USERNAME}?username=${username}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`)
        }
    },

    changeEmail: async (email: string) => {
        const response = await fetch(`${API_BASE_URL}${API.USER.CHANGE_EMAIL}?email=${email}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`)
        }
    },

    changePassword: async (password: string) => {
        const response = await fetch(`${API_BASE_URL}${API.USER.CHANGE_PASSWORD}?password=${password}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`)
        }
    }
}
