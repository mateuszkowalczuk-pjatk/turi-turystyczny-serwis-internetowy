import { Attraction } from '../../../types/attraction.ts'
import { ReservationAttraction } from '../../../types/reservation.ts'

export interface Props {
    reservationId: number
    attractionId: number
    attraction: Attraction
    setReservationAttractions: (
        value: ((prevState: ReservationAttraction[]) => ReservationAttraction[]) | ReservationAttraction[]
    ) => void
    dateFrom: Date
    dateTo: Date
}
