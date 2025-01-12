import { useHooks } from '../hooks/useHooks.ts'
import { Address } from '../types'

export const generateAddress = (address: Address): string => {
    const { t } = useHooks()

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
