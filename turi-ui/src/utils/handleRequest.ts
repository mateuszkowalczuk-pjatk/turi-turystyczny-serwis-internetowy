import { ReservationAttraction, ReservationStatus } from '../types/reservation.ts'

export const defaultParamIfNull = (
    name: string,
    value: string | number | Date | ReservationStatus[] | ReservationAttraction[] | null
): string => {
    return value !== null ? `&${name}=${value}` : ''
}

export const defaultBodyIfNull = (value: ReservationAttraction[] | null): string | undefined => {
    return value ? JSON.stringify(value) : undefined
}
