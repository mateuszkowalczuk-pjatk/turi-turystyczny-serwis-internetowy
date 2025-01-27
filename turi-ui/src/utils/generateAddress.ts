import { Address } from '../types'

export const generateAddress = (address: Address, t: (key: string) => string): string => {
    return (
        t('offer.street') +
        address.street +
        ' ' +
        address.buildingNumber +
        (address.apartmentNumber ? '/' + address.apartmentNumber : '') +
        ', ' +
        address.zipCode +
        ' ' +
        address?.city +
        ', ' +
        address.country
    )
}
