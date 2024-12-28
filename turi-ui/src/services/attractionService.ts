import { API_BASE_URL } from '../config/api'
import { API } from './constants.ts'
import { Attraction } from '../types/attraction.ts'

export const attractionService = {
    getAllByTouristicPlaceId: async (touristicPlaceId: number) => {
        return await fetch(
            `${API_BASE_URL}${API.ATTRACTION.GET_ALL_BY_TOURISTIC_PLACE_ID}?touristicPlaceId=${touristicPlaceId}`,
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
        return await fetch(`${API_BASE_URL}${API.ATTRACTION.GET_BY_ID}/${id}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })
    },

    create: async (attraction: Attraction) => {
        return await fetch(`${API_BASE_URL}${API.ATTRACTION.CREATE}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(attraction),
            credentials: 'include'
        })
    },

    update: async (id: number, attraction: Attraction) => {
        return await fetch(`${API_BASE_URL}${API.ATTRACTION.UPDATE}/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(attraction),
            credentials: 'include'
        })
    },

    delete: async (id: number) => {
        return await fetch(`${API_BASE_URL}${API.ATTRACTION.DELETE}/${id}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })
    }
}
