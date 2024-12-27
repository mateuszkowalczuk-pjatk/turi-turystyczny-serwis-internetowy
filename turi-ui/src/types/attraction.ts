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
    RELAX = 1,
    SPORT = 2,
    RECREATION = 3,
    ENTERTAINMENT = 4,
    FOOD = 5,
    EVENT = 6,
    CHILDREN = 7,
    OTHER = 8
}

export enum PriceType {
    HOUR = 1,
    PERSON = 2,
    ITEM = 3
}
