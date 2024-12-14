import { API_BASE_URL } from '../config/api'
import { API } from './constants.ts'
import { LoginParam, RegisterParam } from '../types'

export const authService = {
    register: async (params: RegisterParam) => {
        return await fetch(`${API_BASE_URL}${API.AUTH.REGISTER}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(params),
            credentials: 'include'
        })
    },

    login: async (params: LoginParam) => {
        return await fetch(`${API_BASE_URL}${API.AUTH.LOGIN}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(params),
            credentials: 'include'
        })
    },

    loginPremium: async (code: string) => {
        return await fetch(`${API_BASE_URL}${API.AUTH.LOGIN_PREMIUM}?code=${code}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })
    },

    authorize: async () => {
        return await fetch(`${API_BASE_URL}${API.AUTH.AUTHORIZE}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })
    },

    refresh: async () => {
        return await fetch(`${API_BASE_URL}${API.AUTH.REFRESH}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })
    },

    logout: async () => {
        return await fetch(`${API_BASE_URL}${API.AUTH.LOGOUT}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })
    }
}
