import { API_BASE_URL } from '../config/api'
import { API } from './constants.ts'
import { Account } from '../types'

export const accountService = {
    get: async (accountId: number) => {
        return await fetch(`${API_BASE_URL}${API.ACCOUNT.GET}/${accountId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })
    },

    getById: async () => {
        return await fetch(`${API_BASE_URL}${API.ACCOUNT.GET_BY_ID}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })
    },

    isAddressExists: async (
        country: string,
        city: string,
        zipCode: string,
        street: string,
        buildingNumber: string,
        apartmentNumber: string | null
    ) => {
        if (apartmentNumber === null) {
            return await fetch(
                `${API_BASE_URL}${API.ACCOUNT.IS_ADDRESS_EXISTS}?country=${country}&city=${city}&zipCode=${zipCode}&street=${street}&buildingNumber=${buildingNumber}`,
                {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    credentials: 'include'
                }
            )
        }
        return await fetch(
            `${API_BASE_URL}${API.ACCOUNT.IS_ADDRESS_EXISTS}?country=${country}&city=${city}&zipCode=${zipCode}&street=${street}&buildingNumber=${buildingNumber}&apartmentNumber=${apartmentNumber}`,
            {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
                credentials: 'include'
            }
        )
    },

    isPhoneNumberExists: async (phoneNumber: string) => {
        return await fetch(`${API_BASE_URL}${API.ACCOUNT.IS_PHONE_NUMBER_EXISTS}?phoneNumber=${phoneNumber}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })
    },

    isPremium: async () => {
        return await fetch(`${API_BASE_URL}${API.ACCOUNT.IS_PREMIUM}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })
    },

    activate: async (code: number) => {
        return await fetch(`${API_BASE_URL}${API.ACCOUNT.ACTIVATE}?code=${code}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })
    },

    update: async (account: Account) => {
        return await fetch(`${API_BASE_URL}${API.ACCOUNT.UPDATE}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(account),
            credentials: 'include'
        })
    }
}
