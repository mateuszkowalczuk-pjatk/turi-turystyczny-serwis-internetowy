export interface Attraction {
    readonly attractionId?: number
    touristicPlaceId: number
    attractionType: AttractionType
    name: string
    description: string
    price: number
    priceType: PriceType
    prepayment: boolean
    cancelReservationDays?: number
    maxPeopleNumber?: number
    maxItems?: number
    dateFrom: Date
    dateTo: Date
    hourFrom: string
    hourTo: string
    daysReservationBefore: number
}

export enum AttractionType {
    UNASSIGNED = 'UNASSIGNED',
    RELAX = 'RELAX',
    SPORT = 'SPORT',
    RECREATION = 'RECREATION',
    ENTERTAINMENT = 'ENTERTAINMENT',
    FOOD = 'FOOD',
    EVENT = 'EVENT',
    CHILDREN = 'CHILDREN',
    OTHER = 'OTHER'
}

export enum PriceType {
    UNASSIGNED = 0,
    HOUR = 1,
    PERSON = 2,
    ITEM = 3
}
