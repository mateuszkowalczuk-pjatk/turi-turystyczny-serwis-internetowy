import { API_BASE_URL } from '../config/api.ts'
import { API } from './constants.ts'
import { SearchMode } from '../types/search.ts'
import { TouristicPlaceType } from '../types/touristicPlace.ts'
import { AttractionType } from '../types/attraction.ts'

export const searchService = {
    search: async (
        mode: SearchMode,
        limit: string,
        touristicPlaceId: string | null,
        rank: string | null,
        query: string | null,
        dateFrom: Date | null,
        dateTo: Date | null,
        touristicPlaceType: TouristicPlaceType | null,
        attractionType: AttractionType | null
    ) => {
        return await fetch(
            `${API_BASE_URL}${API.SEARCH.SEARCH}?mode=${mode}&limit=${limit}&touristicPlaceId=${touristicPlaceId}&rank=${rank}&query=${query}&dateFrom=${dateFrom}&dateTo=${dateTo}&touristicPlaceType=${touristicPlaceType}&attractionType=${attractionType}`,
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
