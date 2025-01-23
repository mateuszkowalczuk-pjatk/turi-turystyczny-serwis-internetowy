import styles from './ReservationReservations.module.css'
import { ReservationDto, ReservationStatus } from '../../../types/reservation.ts'
import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { useEffect, useState } from 'react'
import { reservationService } from '../../../services/reservationService.ts'
import ReservationsItem from '../ReservationsItem'

const ReservationReservations = ({ statuses }: { statuses: ReservationStatus[] }) => {
    const { t } = useHooks()
    const [reservations, setReservations] = useState<ReservationDto[]>([])

    useEffect(() => {
        const fetchReservations = async () => {
            const reservationsResponse = await reservationService.getAllByAccountId(statuses)
            const reservationsData: ReservationDto[] = await reservationsResponse.json()
            setReservations(reservationsData)
        }
        fetchReservations().catch((error) => error)
    }, [statuses])

    return (
        <div className={styles.reservations}>
            {reservations.map((reservation, key) => (
                <ReservationsItem
                    key={key}
                    reservation={reservation}
                />
            ))}
            {reservations.length === 0 && <div className={styles.empty}>{t('reservation.reservations-not-found')}</div>}
        </div>
    )
}

export default ReservationReservations
