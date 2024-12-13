export interface Account {
    readonly accountId?: number
    userId?: number
    addressId: number
    accountType?: AccountType
    activationCode?: number
    activationCodeExpiresAt?: Date
    firstName: string
    lastName: string
    birthDate: Date
    phoneNumber: string
    gender: Gender
}

export enum AccountType {
    INACTIVE = 0,
    NORMAL = 1,
    PREMIUM = 2
}

export enum Gender {
    MALE = 0,
    FEMALE = 1
}
