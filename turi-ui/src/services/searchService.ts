import { API_BASE_URL } from '../config/api.ts'
import { API } from './constants.ts'

export const searchService = {
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
        const touristicPlaceIdParam = touristicPlaceId ? '&touristicPlaceId=' + touristicPlaceId : ''
        const rankParam = rank ? '&rank=' + rank : ''
        const queryParam = query ? '&query=' + query : ''
        const dateFromParam = dateFrom ? '&dateFrom=' + dateFrom : ''
        const dateToParam = dateTo ? '&dateTo=' + dateTo : ''
        const touristicPlaceTypeParam = touristicPlaceType ? '&touristicPlaceType=' + touristicPlaceType : ''
        const attractionTypeParam = attractionType ? '&attractionType=' + attractionType : ''
        return await fetch(
            `${API_BASE_URL}${API.SEARCH.SEARCH}?mode=${mode}&limit=2${touristicPlaceIdParam}${rankParam}${queryParam}${dateFromParam}${dateToParam}${touristicPlaceTypeParam}${attractionTypeParam}`,
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
        return await fetch(`${API_BASE_URL}${API.SEARCH.AUTOCOMPLETE}?mode=${mode}&query=${query}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })
    }
}
