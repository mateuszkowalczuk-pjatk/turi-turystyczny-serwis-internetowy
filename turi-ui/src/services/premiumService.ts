import { API_BASE_URL } from '../config/api'
import { API } from './constants.ts'
import { PaymentMethod, PremiumCompanyParam, PremiumVerifyParam } from '../types'

export const premiumService = {
    getOffer: async () => {
        return await fetch(`${API_BASE_URL}${API.PREMIUM.GET_OFFER}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })
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

    getAccountId: async (premiumId: number) => {
        return await fetch(`${API_BASE_URL}${API.PREMIUM.GET_ACCOUNT_ID}/${premiumId}`, {
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
    },

    verify: async (params: PremiumVerifyParam) => {
        return await fetch(`${API_BASE_URL}${API.PREMIUM.VERIFY}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(params),
            credentials: 'include'
        })
    },

    pay: async (method: PaymentMethod) => {
        return await fetch(`${API_BASE_URL}${API.PREMIUM.PAY}?method=${method}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })
    },

    renew: async (method: PaymentMethod) => {
        return await fetch(`${API_BASE_URL}${API.PREMIUM.RENEW}?method=${method}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })
    },

    cancel: async () => {
        return await fetch(`${API_BASE_URL}${API.PREMIUM.CANCEL}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })
    },

    updateCompanyDetails: async (params: PremiumCompanyParam) => {
        return await fetch(`${API_BASE_URL}${API.PREMIUM.UPDATE_COMPANY_DETAILS}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(params),
            credentials: 'include'
        })
    }
}
