import { useEffect, useState } from 'react'
import { ReservationDto, ReservationStatus } from '../../../types/reservation.ts'
import styles from '../TourismOffers/TourismOffers.module.css'
import TourismReservationsItem from '../TourismReservationsItem'
import { reservationService } from '../../../services/reservationService.ts'

interface Props {
    touristicPlaceId: number
    statuses: ReservationStatus[]
}

const TourismReservations = ({ touristicPlaceId, statuses }: Props) => {
    const [reservations, setReservations] = useState<ReservationDto[]>()

    useEffect(() => {
        const fetchData = async () => {
            const response = await reservationService.getAllByTouristicPlaceId(touristicPlaceId, statuses)
            const data = await response.json()
            setReservations(data)
        }
        fetchData().catch((error) => error)
    }, [])

    return (
        <div className={styles.offers}>
            {reservations &&
                reservations.map((reservation, index) => (
                    <TourismReservationsItem
                        key={index}
                        reservation={reservation}
                    />
                ))}
        </div>
    )
}

export default TourismReservations
