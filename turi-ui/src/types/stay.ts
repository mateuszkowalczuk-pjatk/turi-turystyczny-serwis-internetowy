export interface Stay {
    readonly stayId?: number
    touristicPlaceId: number
    name: string
    description: string
    price: number
    peopleNumber: number
    dateFrom: Date
    dateTo?: Date
}

export interface StayDto {
    readonly stayId?: number
    touristicPlaceId: number
    name: string
    description: string
    price: number
    peopleNumber: number
    dateFrom: Date
    dateTo?: Date
    stayInformations: StayInformation[]
}

export interface StayInformation {
    readonly stayInformationId?: number
    stayId: number
    information: string
}
