import { useHooks } from './useHooks.ts'
import { useEffect, useState } from 'react'
import { Stay } from '../types/stay.ts'
import { Address } from '../types'
import { Reservation } from '../types/reservation.ts'
import { TouristicPlace } from '../types/touristicPlace.ts'
import { addressService } from '../services/addressService.ts'
import { reservationService } from '../services/reservationService.ts'
import { touristicPlaceService } from '../services/touristicPlaceService.ts'
import { startLoading, stopLoading } from '../store/slices/loading.ts'

export const useReservation = (
    stay: Stay,
    dateFrom: Date,
    dateTo: Date
): {
    reservation: Reservation | undefined
    touristicPlace: TouristicPlace | undefined
    address: Address | undefined
    price: number | undefined
    request: string | null
    setRequest: (value: ((prevState: string | null) => string | null) | string | null) => void
} => {
    const { dispatch, navigate } = useHooks()
    const [reservation, setReservation] = useState<Reservation>()
    const [touristicPlace, setTouristicPlace] = useState<TouristicPlace>()
    const [address, setAddress] = useState<Address>()
    const [price, setPrice] = useState<number>()
    const [request, setRequest] = useState<string | null>(null)

    useEffect(() => {
        dispatch(startLoading())
        const createReservationWithPrice = async () => {
            if (stay.stayId) {
                const reservationResponse = await reservationService.create(stay.stayId, dateFrom, dateTo)
                const reservationData: Reservation = await reservationResponse.json()
                setReservation(reservationData)
                if (reservationData.request) setRequest(reservationData.request)
                if (reservationData.reservationId) {
                    const priceResponse = await reservationService.getPrice(reservationData.reservationId)
                    const priceData: number = await priceResponse.json()
                    setPrice(priceData)
                } else navigate(-1)
            } else navigate(-1)
        }
        createReservationWithPrice().catch((error) => error)

        const fetchTouristicPlaceWithAddress = async () => {
            const touristicPlaceResponse = await touristicPlaceService.getById(stay.touristicPlaceId)
            const touristicPlaceData: TouristicPlace = await touristicPlaceResponse.json()
            setTouristicPlace(touristicPlaceData)
            if (touristicPlaceData.addressId) {
                const addressResponse = await addressService.getById(touristicPlaceData.addressId)
                const addressData: Address = await addressResponse.json()
                setAddress(addressData)
            } else navigate(-1)
        }
        fetchTouristicPlaceWithAddress().catch((error) => error)
        dispatch(stopLoading())
    }, [stay, dateFrom, dateTo, navigate, dispatch])

    return { reservation, touristicPlace, address, price, request, setRequest }
}
