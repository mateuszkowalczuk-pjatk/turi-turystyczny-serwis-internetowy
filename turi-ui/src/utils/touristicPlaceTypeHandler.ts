import { TouristicPlaceType } from '../types/touristicPlace.ts'

export const touristicPlaceTypeHandler = (type: TouristicPlaceType, t: (key: string) => string) => {
    switch (type) {
        case TouristicPlaceType.GUESTHOUSE:
            return t('tourism.touristic-place-type-guesthouse')
        case TouristicPlaceType.APARTMENT:
            return t('tourism.touristic-place-type-apartment')
        case TouristicPlaceType.COTTAGES:
            return t('tourism.touristic-place-type-cottages')
        case TouristicPlaceType.HOTEL:
            return t('tourism.touristic-place-type-hotel')
        case TouristicPlaceType.BB:
            return t('tourism.touristic-place-type-bb')
        case TouristicPlaceType.HOSTEL:
            return t('tourism.touristic-place-type-hostel')
        default:
            return ''
    }
}
