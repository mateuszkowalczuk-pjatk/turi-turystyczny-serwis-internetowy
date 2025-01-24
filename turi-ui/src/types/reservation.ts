export interface Reservation {
    readonly reservationId: number
    stayId: number
    accountId: number
    dateFrom: Date
    dateTo: Date
    price: number
    checkInTime: string
    request: string
    rating: number
    opinion: string
    modifyDate: string
    status: ReservationStatus
}

export interface ReservationAttraction {
    readonly reservationAttractionId: number
    reservationId: number
    attractionId: number
    dateFrom: Date
    dateTo: Date
    hourFrom: string
    hourTo: string
    people: number
    items: number
    message: string
    price: number
    modifyDate: string
    status: ReservationStatus
}

export interface ReservationDto {
    reservation: Reservation
    attractions: ReservationAttraction[]
}

export enum ReservationStatus {
    LOCKED = 0,
    UNPAID = 1,
    PAY_ON_SITE = 2,
    RESERVATION = 'RESERVATION',
    RESERVATION_UNPAID_DATE_EXTENSION = 4,
    REALIZATION = 'REALIZATION',
    REALIZATION_PAY_ON_SITE_DATE_EXTENSION = 6,
    REALIZATION_UNPAID_DATE_EXTENSION = 7,
    REALIZED = 'REALIZED',
    CANCELED = 9
}

export enum ReservationMode {
    INITIAL = 'INITIAL',
    DATE_EXTENSION = 'DATE_EXTENSION',
    ATTRACTIONS = 'ATTRACTIONS'
}
