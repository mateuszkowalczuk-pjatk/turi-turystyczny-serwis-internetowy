import { API_BASE_URL } from '../config/api'
import { API } from './constants.ts'
import { Stay, StayDto, StayInformation } from '../types/stay.ts'

export const stayService = {
    getAllByTouristicPlaceId: async (touristicPlaceId: number) => {
        return await fetch(
            `${API_BASE_URL}${API.STAY.GET_ALL_BY_TOURISTIC_PLACE_ID}?touristicPlaceId=${touristicPlaceId}`,
            {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
                credentials: 'include'
            }
        )
    },

    getById: async (id: number) => {
        return await fetch(`${API_BASE_URL}${API.STAY.GET_BY_ID}/${id}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })
    },

    create: async (stay: StayDto) => {
        return await fetch(`${API_BASE_URL}${API.STAY.CREATE}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(stay),
            credentials: 'include'
        })
    },

    createStayInformation: async (stayInformation: StayInformation) => {
        return await fetch(`${API_BASE_URL}${API.STAY.CREATE_STAY_INFORMATION}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(stayInformation),
            credentials: 'include'
        })
    },

    update: async (id: number, stay: Stay) => {
        return await fetch(`${API_BASE_URL}${API.STAY.UPDATE}/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(stay),
            credentials: 'include'
        })
    },

    delete: async (id: number) => {
        return await fetch(`${API_BASE_URL}${API.STAY.DELETE}/${id}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })
    },

    deleteStayInformation: async (stayInformationId: number) => {
        return await fetch(`${API_BASE_URL}${API.STAY.DELETE_STAY_INFORMATION}/${stayInformationId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })
    }
}
