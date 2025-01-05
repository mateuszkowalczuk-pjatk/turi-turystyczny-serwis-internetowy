export interface Premium {
    readonly premiumId: number
    accountId: number
    companyName: string
    nip: string
    bankAccountNumber: string
    buyDate: Date
    expiresDate: Date
    status: PremiumStatus
    loginCode: number
    loginToken: string
    loginExpiresAt: Date
}

export interface PremiumCompanyParam {
    companyName: string
    nip: string
    bankAccountNumber: string
}

export interface PremiumOffer {
    length: number
    price: number
}

export interface PremiumVerifyParam {
    firstName: string
    lastName: string
    bankAccountNumber: string
    companyName: string
    nip: string
}

export enum PremiumStatus {
    UNPAID = 0,
    ACTIVE = 1,
    EXPIRED = 2
}
