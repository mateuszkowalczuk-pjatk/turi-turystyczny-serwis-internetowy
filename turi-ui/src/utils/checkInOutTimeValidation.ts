export const checkInOutTimeValidation = (checkTime: string): boolean => {
    return /^\d{2}:\d{2} - \d{2}:\d{2}$/.test(checkTime)
}
