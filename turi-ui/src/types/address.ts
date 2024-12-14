export interface Address {
    readonly addressId?: number
    country: string
    city: string
    zipCode: string
    street: string
    buildingNumber: string
    apartmentNumber?: number
}
