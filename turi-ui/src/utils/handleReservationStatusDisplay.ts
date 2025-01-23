import { ReservationStatus } from '../types/reservation.ts'

export const handleReservationStatusDisplay = (status: ReservationStatus, t: (key: string) => string): string => {
    switch (status) {
        case ReservationStatus.RESERVATION:
            return t('reservation.reservation-status-reservation')
        case ReservationStatus.REALIZATION:
            return t('reservation.reservation-status-realization')
        case ReservationStatus.REALIZED:
            return t('reservation.reservation-status-realized')
        default:
            return ''
    }
}
