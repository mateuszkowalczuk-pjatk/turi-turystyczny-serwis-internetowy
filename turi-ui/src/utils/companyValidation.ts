export const nipValidation = (nip: string): boolean => {
    return !/^\d{10}$/.test(nip)
}

export const bankAccountNumberValidation = (bankAccountNumber: string): boolean => {
    return !/^\d{26}$/.test(bankAccountNumber)
}
