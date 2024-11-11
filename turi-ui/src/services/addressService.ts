import { API_BASE_URL } from '../config/api'
import { API } from './constants.ts'

interface Address {
    addressId?: number
    country: string
    city: string
    zipCode: string
    street: string
    buildingNumber: string
    apartmentNumber?: number
}

export const addressService = {
    getById: async (addressId: number) => {
        return await fetch(`${API_BASE_URL}${API.ADDRESS.GET_BY_ID}/${addressId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })
    },

    create: async (address: Address) => {
        return await fetch(`${API_BASE_URL}${API.ADDRESS.CREATE}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(address),
            credentials: 'include'
        })
    }
}
