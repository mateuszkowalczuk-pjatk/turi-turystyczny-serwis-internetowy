export interface TouristicPlace {
    readonly touristicPlaceId?: number
    premiumId: number
    addressId?: number
    touristicPlaceType?: TouristicPlaceType
    name?: string
    description?: string
    information?: string
    ownerDescription?: string
    checkInTimeFrom?: string
    checkInTimeTo?: string
    checkOutTimeFrom?: string
    checkOutTimeTo?: string
    prepayment?: boolean
    cancelReservationDays?: number
}

export interface GuaranteedService {
    readonly guaranteedServiceId?: number
    touristicPlaceId: number
    service: string
}

export enum TouristicPlaceType {
    UNASSIGNED = 'UNASSIGNED',
    GUESTHOUSE = 'GUESTHOUSE',
    APARTMENT = 'APARTMENT',
    COTTAGES = 'COTTAGES',
    HOTEL = 'HOTEL',
    BB = 'BB',
    HOSTEL = 'HOSTEL'
}
