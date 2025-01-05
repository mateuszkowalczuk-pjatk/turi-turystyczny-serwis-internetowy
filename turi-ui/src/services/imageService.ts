import { API_BASE_URL } from '../config/api'
import { API } from './constants.ts'
import { ImageMode } from '../types/image.ts'

export const imageService = {
    getByAccount: async (accountId: number) => {
        return await fetch(`${API_BASE_URL}${API.IMAGE.GET_BY_ACCOUNT}?accountId=${accountId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })
    },

    getByAccountId: async () => {
        return await fetch(`${API_BASE_URL}${API.IMAGE.GET_BY_ACCOUNT_ID}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })
    },

    getAllByTouristicPlaceId: async (touristicPlaceId: number) => {
        return await fetch(
            `${API_BASE_URL}${API.IMAGE.GET_ALL_BY_TOURISTIC_PLACE_ID}?touristicPlaceId=${touristicPlaceId}`,
            {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
                credentials: 'include'
            }
        )
    },

    getAllByStayId: async (stayId: number) => {
        return await fetch(`${API_BASE_URL}${API.IMAGE.GET_ALL_BY_STAY_ID}?stayId=${stayId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })
    },

    getAllByAttractionId: async (attractionId: number) => {
        return await fetch(`${API_BASE_URL}${API.IMAGE.GET_ALL_BY_ATTRACTION_ID}?attractionId=${attractionId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })
    },

    upload: async (file: File, mode: ImageMode, id?: number) => {
        const data = new FormData()
        data.append('file', file)
        data.append('mode', mode)
        if (id) data.append('id', id.toString())

        return await fetch(`${API_BASE_URL}${API.IMAGE.UPLOAD}`, {
            method: 'POST',
            body: data,
            credentials: 'include'
        })
    },

    deleteById: async (id: number) => {
        return await fetch(`${API_BASE_URL}${API.IMAGE.DELETE_BY_ID}/${id}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })
    }
}
