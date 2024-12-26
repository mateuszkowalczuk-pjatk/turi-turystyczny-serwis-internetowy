export interface Image {
    readonly imageId?: number
    accountId?: number
    touristicPlaceId?: number
    stayId?: number
    attractionId?: number
    path: string
}

export enum ImageMode {
    ACCOUNT = 'ACCOUNT',
    TOURISTICPLACE = 'TOURISTICPLACE',
    STAY = 'STAY',
    ATTRACTION = 'ATTRACTION'
}
