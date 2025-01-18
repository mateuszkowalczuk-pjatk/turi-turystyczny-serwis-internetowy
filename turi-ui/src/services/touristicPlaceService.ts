import { API_BASE_URL } from '../config/api'
import { API } from './constants.ts'
import { GuaranteedService, TouristicPlace } from '../types/touristicPlace.ts'
import { defaultParamIfNull } from '../utils/handleRequest.ts'

export const touristicPlaceService = {
    getByPremiumId: async () => {
        return await fetch(`${API_BASE_URL}${API.TOURISTIC_PLACE.GET_BY_PREMIUM_ID}`, {
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
        return await fetch(
            `${API_BASE_URL}${API.ACCOUNT.IS_ADDRESS_EXISTS}?country=${country}&city=${city}&zipCode=${zipCode}&street=${street}&buildingNumber=${buildingNumber}${defaultParamIfNull('apartmentNumber', apartmentNumber)}`,
            {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
                credentials: 'include'
            }
        )
    },

    getAllGuaranteedServices: async () => {
        return await fetch(`${API_BASE_URL}${API.TOURISTIC_PLACE.GET_ALL_GUARANTEED_SERVICES}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })
    },

    create: async () => {
        return await fetch(`${API_BASE_URL}${API.TOURISTIC_PLACE.CREATE}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })
    },

    createGuaranteedService: async (guaranteedService: GuaranteedService) => {
        return await fetch(`${API_BASE_URL}${API.TOURISTIC_PLACE.CREATE_GUARANTEED_SERVICE}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(guaranteedService),
            credentials: 'include'
        })
    },

    updateDetails: async (id: number, touristicPlace: TouristicPlace) => {
        return await fetch(`${API_BASE_URL}${API.TOURISTIC_PLACE.UPDATE_DETAILS}/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(touristicPlace),
            credentials: 'include'
        })
    },

    deleteGuaranteedService: async (guaranteedServiceId: number) => {
        return await fetch(
            `${API_BASE_URL}${API.TOURISTIC_PLACE.DELETE_GUARANTEED_SERVICE}?guaranteedServiceId=${guaranteedServiceId}`,
            {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                },
                credentials: 'include'
            }
        )
    }
}
