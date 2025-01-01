import { GuaranteedService, TouristicPlace } from './touristicPlace.ts'
import { StayDto } from './stay.ts'
import { Attraction } from './attraction.ts'

export interface Search {
    searchTouristicPlaces: SearchTouristicPlaces
    touristicPlaceId: number
    rank: number
}

export interface SearchTouristicPlaces {
    touristicPlace: TouristicPlace
    guaranteedServices: GuaranteedService[]
    stays: StayDto[]
    attractions: Attraction[]
}

export enum SearchMode {
    ALL = 'ALL',
    STAY = 'STAY',
    ATTRACTION = 'ATTRACTION'
}
