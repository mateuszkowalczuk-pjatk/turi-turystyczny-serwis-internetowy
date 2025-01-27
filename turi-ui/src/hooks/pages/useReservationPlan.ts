import { useHooks } from '../shared/useHooks.ts'
import { useEffect, useState } from 'react'
import { Attraction } from '../../types/attraction.ts'
import { ReservationAttraction } from '../../types/reservation.ts'
import { reservationService } from '../../services/reservationService.ts'
import { startLoading, stopLoading } from '../../store/slices/loading.ts'

export const useReservationPlan = (
    reservationId: number,
    touristicPlaceId: number,
    dateFrom: string,
    dateTo: string
): {
    reservationAttractions: ReservationAttraction[]
    setReservationAttractions: (
        value: ((prevState: ReservationAttraction[]) => ReservationAttraction[]) | ReservationAttraction[]
    ) => void
    attractions: Attraction[]
} => {
    const { dispatch } = useHooks()
    const [reservationAttractions, setReservationAttractions] = useState<ReservationAttraction[]>([])
    const [attractions, setAttractions] = useState<Attraction[]>([])

    useEffect(() => {
        dispatch(startLoading())
        const fetchReservationAttractionsWithAttractions = async () => {
            const reservationAttractionsResponse =
                await reservationService.getAllAttractionsByReservationId(reservationId)
            const reservationAttractionsData: ReservationAttraction[] = await reservationAttractionsResponse.json()
            setReservationAttractions(reservationAttractionsData)
            const attractionsResponse = await reservationService.getAllTouristicPlaceAttractionsAvailableInDate(
                touristicPlaceId,
                dateFrom,
                dateTo
            )
            const attractionsData: Attraction[] = await attractionsResponse.json()
            setAttractions(attractionsData)
        }
        fetchReservationAttractionsWithAttractions().catch((error) => error)
        dispatch(stopLoading())
    }, [dispatch, reservationId, touristicPlaceId, dateFrom, dateTo])

    return { reservationAttractions, setReservationAttractions, attractions }
}
