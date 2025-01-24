import { useHooks } from '../shared/useHooks.ts'
import { useEffect, useState } from 'react'
import { Stay } from '../../types/stay.ts'
import { Address } from '../../types'
import { TouristicPlace } from '../../types/touristicPlace.ts'
import { Reservation, ReservationAttraction } from '../../types/reservation.ts'
import { addressService } from '../../services/addressService.ts'
import { reservationService } from '../../services/reservationService.ts'
import { touristicPlaceService } from '../../services/touristicPlaceService.ts'
import { startLoading, stopLoading } from '../../store/slices/loading.ts'

export const useReservation = (
    stay: Stay,
    dateFrom: string,
    dateTo: string
): {
    reservation: Reservation | undefined
    reservationAttractions: ReservationAttraction[]
    touristicPlace: TouristicPlace | undefined
    address: Address | undefined
    price: number | undefined
    request: string | null
    setRequest: (value: ((prevState: string | null) => string | null) | string | null) => void
} => {
    const { dispatch, navigate } = useHooks()
    const [reservation, setReservation] = useState<Reservation>()
    const [reservationAttractions, setReservationAttractions] = useState<ReservationAttraction[]>([])
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
                    const reservationAttractionsResponse = await reservationService.getAllAttractionsByReservationId(
                        reservationData.reservationId
                    )
                    const reservationAttractionsData: ReservationAttraction[] =
                        await reservationAttractionsResponse.json()
                    setReservationAttractions(reservationAttractionsData)
                }
            }
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
            }
        }
        fetchTouristicPlaceWithAddress().catch((error) => error)
        dispatch(stopLoading())
    }, [stay, dateFrom, dateTo, navigate, dispatch])

    return { reservation, reservationAttractions, touristicPlace, address, price, request, setRequest }
}
