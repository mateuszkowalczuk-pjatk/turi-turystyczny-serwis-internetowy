import { API_BASE_URL } from '../config/api'
import { API } from './constants.ts'
import { PaymentMethod } from '../types'
import { ReservationAttraction, ReservationMode, ReservationStatus } from '../types/reservation.ts'
import { defaultBodyIfNull, defaultParamIfNull } from '../utils/handleRequest.ts'

export const reservationService = {
    getAllAttractionsByReservationId: async (reservationId: number) => {
        return await fetch(`${API_BASE_URL}${API.RESERVATION.GET_ALL_ATTRACTIONS_BY_RESERVATION_ID}/${reservationId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })
    },

    getWithAttractionsById: async (id: number) => {
        return await fetch(`${API_BASE_URL}${API.RESERVATION.GET_WITH_ATTRACTION_BY_ID}/${id}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })
    },

    getPrice: async (id: number) => {
        return await fetch(`${API_BASE_URL}${API.RESERVATION.GET_PRICE}/${id}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })
    },

    getAllTouristicPlaceStaysAvailableInDate: async (touristicPlaceId: number, dateFrom: Date, dateTo: Date) => {
        return await fetch(
            `${API_BASE_URL}${API.RESERVATION.GET_ALL_TOURISTIC_PLACE_STAYS_AVAILABLE_IN_DATE}?touristicPlaceId=${touristicPlaceId}&dateFrom=${dateFrom}&dateTo=${dateTo}`,
            {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
                credentials: 'include'
            }
        )
    },

    getAllTouristicPlaceAttractionsAvailableInDate: async (touristicPlaceId: number, dateFrom: Date, dateTo: Date) => {
        return await fetch(
            `${API_BASE_URL}${API.RESERVATION.GET_ALL_TOURISTIC_PLACE_ATTRACTIONS_AVAILABLE_IN_DATE}?touristicPlaceId=${touristicPlaceId}&dateFrom=${dateFrom}&dateTo=${dateTo}`,
            {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
                credentials: 'include'
            }
        )
    },

    checkPayment: async (reservationId: string | null, modes: ReservationMode[]) => {
        return await fetch(
            `${API_BASE_URL}${API.RESERVATION.CHECK_PAYMENT}?modes=${modes}${defaultParamIfNull('reservationId', reservationId)}`,
            {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
                credentials: 'include'
            }
        )
    },
    getAllByAccountId: async (statuses: ReservationStatus[]) => {
        return await fetch(`${API_BASE_URL}${API.RESERVATION.GET_ALL_BY_ACCOUNT_ID}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(statuses),
            credentials: 'include'
        })
    },

    getAllByTouristicPlaceId: async (touristicPlaceId: number, statuses: ReservationStatus[] | null) => {
        return await fetch(
            `${API_BASE_URL}${API.RESERVATION.GET_ALL_BY_TOURISTIC_PLACE_ID}?touristicPlaceId=${touristicPlaceId}`,
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(statuses),
                credentials: 'include'
            }
        )
    },

    create: async (stayId: number, dateFrom: Date, dateTo: Date) => {
        return await fetch(
            `${API_BASE_URL}${API.RESERVATION.CREATE}?stayId=${stayId}&dateFrom=${dateFrom}&dateTo=${dateTo}`,
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                credentials: 'include'
            }
        )
    },

    createAttraction: async (
        id: number,
        attractionId: number,
        dateFrom: Date,
        dateTo: Date,
        hourFrom: string,
        hourTo: string,
        people: number | null,
        items: number | null,
        message: string | null
    ) => {
        return await fetch(
            `${API_BASE_URL}${API.RESERVATION.CREATE_ATTRACTION}?id=${id}&attractionId=${attractionId}&dateFrom=${dateFrom}&dateTo=${dateTo}&hourFrom=${hourFrom}&hourTo=${hourTo}${defaultParamIfNull('people', people)}${defaultParamIfNull('items', items)}${defaultParamIfNull('message', message)}`,
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                credentials: 'include'
            }
        )
    },

    pay: async (
        id: number,
        method: PaymentMethod,
        mode: ReservationMode,
        dateTo: Date | null,
        reservationAttractions: ReservationAttraction[] | null
    ) => {
        return await fetch(
            `${API_BASE_URL}${API.RESERVATION.PAY}/${id}?method=${method}&mode=${mode}${defaultParamIfNull('dateTo', dateTo)}`,
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: defaultBodyIfNull(reservationAttractions),
                credentials: 'include'
            }
        )
    },

    makePayOnSite: async (
        id: number,
        mode: ReservationMode,
        dateTo: Date | null,
        reservationAttractions: ReservationAttraction[] | null
    ) => {
        return await fetch(
            `${API_BASE_URL}${API.RESERVATION.MAKE_PAY_ON_SITE}?id=${id}&mode=${mode}${defaultParamIfNull('dateTo', dateTo)}`,
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: defaultBodyIfNull(reservationAttractions),
                credentials: 'include'
            }
        )
    },

    payOnSite: async (id: number, mode: ReservationMode, reservationAttractions: ReservationAttraction[] | null) => {
        return await fetch(
            `${API_BASE_URL}${API.RESERVATION.MAKE_PAY_ON_SITE}?id=${id}&mode=${mode}${defaultParamIfNull('reservationAttractions', reservationAttractions)}`,
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: defaultBodyIfNull(reservationAttractions),
                credentials: 'include'
            }
        )
    },

    updateDetails: async (id: number, request: string | null) => {
        return await fetch(
            `${API_BASE_URL}${API.RESERVATION.UPDATE_DETAILS}/${id}?${defaultParamIfNull('request', request)}`,
            {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                credentials: 'include'
            }
        )
    },

    updateOpinion: async (id: number, rating: number, opinion: string) => {
        return await fetch(
            `${API_BASE_URL}${API.RESERVATION.UPDATE_DETAILS}/${id}?rating=${rating}&opinion=${opinion}`,
            {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                credentials: 'include'
            }
        )
    },

    updateDateTo: async (id: number, dateTo: Date) => {
        return await fetch(`${API_BASE_URL}${API.RESERVATION.UPDATE_DATE_TO}/${id}?dateTo=${dateTo}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })
    },

    cancel: async (id: number) => {
        return await fetch(`${API_BASE_URL}${API.RESERVATION.CANCEL}/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })
    },

    cancelAttraction: async (reservationAttractionId: number) => {
        return await fetch(
            `${API_BASE_URL}${API.RESERVATION.CANCEL_ATTRACTION}?reservationAttractionId=${reservationAttractionId}`,
            {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                credentials: 'include'
            }
        )
    },

    deleteAttraction: async (reservationAttractionId: number) => {
        return await fetch(
            `${API_BASE_URL}${API.RESERVATION.DELETE_ATTRACTION}?reservationAttractionId=${reservationAttractionId}`,
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
