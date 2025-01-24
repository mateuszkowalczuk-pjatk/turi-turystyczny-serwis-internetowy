import { API_BASE_URL } from '../config/api.ts'
import { API } from './constants.ts'
import { defaultParamIfNull } from '../utils/handleRequest.ts'

export const offerService = {
    search: async (
        mode: string,
        touristicPlaceId: string | null,
        rank: string | null,
        query: string | null,
        dateFrom: string | null,
        dateTo: string | null,
        touristicPlaceType: string | null,
        attractionType: string | null
    ) => {
        return await fetch(
            `${API_BASE_URL}${API.OFFER.SEARCH}?mode=${mode}&limit=2${defaultParamIfNull('touristicPlaceId', touristicPlaceId)}${defaultParamIfNull('rank', rank)}${defaultParamIfNull('query', query)}${defaultParamIfNull('dateFrom', dateFrom)}${defaultParamIfNull('dateTo', dateTo)}${defaultParamIfNull('touristicPlaceType', touristicPlaceType)}${defaultParamIfNull('attractionType', attractionType)}`,
            {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
                credentials: 'include'
            }
        )
    },

    autocomplete: async (mode: string, query: string) => {
        return await fetch(`${API_BASE_URL}${API.OFFER.AUTOCOMPLETE}?mode=${mode}&query=${query}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })
    },

    getAllFavouriteByAccount: async () => {
        return await fetch(`${API_BASE_URL}${API.OFFER.GET_ALL_FAVOURITE_BY_ACCOUNT}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })
    },

    isFavouriteForAccount: async (touristicPlaceId: number) => {
        return await fetch(
            `${API_BASE_URL}${API.OFFER.IS_FAVOURITE_FOR_ACCOUNT}?touristicPlaceId=${touristicPlaceId}`,
            {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
                credentials: 'include'
            }
        )
    },

    addFavouriteForAccount: async (touristicPlaceId: number) => {
        return await fetch(
            `${API_BASE_URL}${API.OFFER.ADD_FAVOURITE_FOR_ACCOUNT}?touristicPlaceId=${touristicPlaceId}`,
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                credentials: 'include'
            }
        )
    },

    deleteFavouriteForAccount: async (touristicPlaceId: number) => {
        return await fetch(
            `${API_BASE_URL}${API.OFFER.DELETE_FAVOURITE_FOR_ACCOUNT}?touristicPlaceId=${touristicPlaceId}`,
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
