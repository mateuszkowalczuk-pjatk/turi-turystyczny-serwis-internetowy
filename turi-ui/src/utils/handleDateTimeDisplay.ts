export const handleDateDisplay = (date: string) => {
    return new Intl.DateTimeFormat('pl-PL', { day: 'numeric', month: 'long', year: 'numeric' }).format(new Date(date))
}

export const handleTimeDisplay = (time: string) => {
    return `${time[0].toString().padStart(2, '0')}:${time[1].toString().padStart(2, '0')}`
}
