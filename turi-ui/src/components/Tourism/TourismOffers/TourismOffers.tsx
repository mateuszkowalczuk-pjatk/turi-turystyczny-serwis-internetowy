import { useEffect, useState } from 'react'
import TourismStayOfferItem from '../TourismStayOfferItem'
import { Stay } from '../../../types/stay.ts'
import { stayService } from '../../../services/stayService.ts'
import styles from './TourismOffers.module.css'

const TourismOffers = ({ touristicPlaceId }: { touristicPlaceId: number }) => {
    const [stays, setStays] = useState<Stay[]>()

    useEffect(() => {
        const fetchStays = async () => {
            const staysResponse = await stayService.getAllByTouristicPlaceId(touristicPlaceId)
            if (staysResponse.status === 200) {
                const staysData = await staysResponse.json()
                setStays(staysData)
            }
        }

        fetchStays().catch((error) => error)
    }, [])

    return (
        <div className={styles.offers}>
            {stays &&
                stays.map((stay, index) => (
                    <TourismStayOfferItem
                        stay={stay}
                        index={index}
                    />
                ))}
        </div>
    )
}

export default TourismOffers
