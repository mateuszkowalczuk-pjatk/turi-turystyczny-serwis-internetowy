import { API_BASE_URL } from '../config/api'
import { API } from './constants.ts'

export const premiumService = {
    getOffer: async (): Promise<Offer> => {
        return await fetch(`${API_BASE_URL}${API.PREMIUM.GET_OFFER}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        }).then((response) => response.json())
    },

    getByAccount: async () => {
        return await fetch(`${API_BASE_URL}${API.PREMIUM.GET_BY_ACCOUNT}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })
    },

    isExistsForAccount: async () => {
        return await fetch(`${API_BASE_URL}${API.PREMIUM.IS_EXISTS_FOR_ACCOUNT}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })
    },

    checkPayment: async () => {
        return await fetch(`${API_BASE_URL}${API.PREMIUM.CHECK_PAYMENT}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })
    }
}
